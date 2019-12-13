package com.zlatenov.videoproviderservice.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.zlatenov.spoilerfreesportsapi.model.dto.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.VideoDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.VideosDto;
import com.zlatenov.videoproviderservice.auth.Auth;
import com.zlatenov.videoproviderservice.model.Channel;
import com.zlatenov.videoproviderservice.model.SearchOptions;
import com.zlatenov.videoproviderservice.model.Video;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author Angel Zlatenov
 */
@Service
@AllArgsConstructor
public class VideoService {

    private static final String CHANNELS_JSON_FILE_PATH = "/home/angel/IdeaProjects/SpoilerFreeNba/Video-provider-service/src/main/resources/channels.json";
    private Gson gson;

    /**
     * Define a global variable that identifies the name of a file that
     * contains the developer's API key.
     */
    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;

    /**
     * Initialize a YouTube object to search for videos on YouTube. Then
     * display the name and thumbnail image of each video in the result set.
     *
     * @return
     */
    public VideosDto getVideosForGame(GameDto gameDto) {
        List<Channel> channels;
        try {
            channels = createListOfChannels();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        List<SearchOptions> searchOptions = createSearchOptionsForGame(gameDto, channels);
        List<Video> videoList = composeVideoList(searchOptions);

        return VideosDto.builder().videoList(transformVideosToDtoList(videoList)).build();
    }

    private List<VideoDto> transformVideosToDtoList(List<Video> videoList) {
        return videoList.stream().map(this::transformToVideoDto).collect(Collectors.toList());
    }

    private VideoDto transformToVideoDto(Video video) {
        return VideoDto.builder().duration(video.getDuration()).id(video.getId()).build();
    }

    private List<Video> composeVideoList(List<SearchOptions> searchOptions) {
        List<Video> videoList = new ArrayList<>();
        for (SearchOptions searchOption : searchOptions) {
            Video video = searchForVideo(searchOption);
            if (video != null) {
                videoList.add(video);
            }
        }
        return videoList;
    }

    private List<Channel> createListOfChannels() throws FileNotFoundException {
        Type channelListType = new TypeToken<List<Channel>>() {
        }.getType();
        JsonReader reader = new JsonReader(new FileReader(CHANNELS_JSON_FILE_PATH));
        return gson.fromJson(reader, channelListType);
    }

    private List<SearchOptions> createSearchOptionsForGame(GameDto gameDto, List<Channel> channels) {
        List<SearchOptions> searchOptions = new ArrayList<>();
        channels.forEach(channel -> searchOptions.addAll(createSearchOptionForGame(channel, gameDto)));
        return searchOptions;
    }

    private List<SearchOptions> createSearchOptionForGame(Channel channel, GameDto gameDto) {
        List<SearchOptions> searchOptions = new ArrayList<>();
        String videoName = composeVideoName(gameDto);
        channel.getDurations()
                .forEach(duration -> searchOptions.add(SearchOptions.builder()
                                                               .channel(channel.getId())
                                                               .date(gameDto.getDate())
                                                               .videoName(videoName)
                                                               .duration(duration)
                                                               .build()));
        return searchOptions;
    }

    private String composeVideoName(GameDto gameDto) {
        return gameDto.getHomeTeamName() + " " + gameDto.getAwayTeamName() + " highlights";
    }

    private Video searchForVideo(SearchOptions searchOptions) {
        Properties properties = new Properties();
        try {
            InputStream in = VideoService.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                                       + " : " + e.getMessage());
            System.exit(1);
        }

        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search-sample").build();

            // Prompt the user to enter a query term.
            //String queryTerm = String.format("%s %s Full Game Highlights",gameDto.getHomeTeamName(), gameDto.getAwayTeamName());

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet").setPublishedAfter(
                    new DateTime(searchOptions.getDate()));

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(searchOptions.getVideoName());

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");
            search.setVideoDuration(searchOptions.getDuration());
            search.setChannelId(searchOptions.getChannel()); //UC8ndn9yAGs5L8NqKUBKzfyw

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (CollectionUtils.isNotEmpty(searchResultList)) {
                SearchResult searchResult = searchResultList.get(0);
                return Video.builder()
                        .id(searchResult.getId().getVideoId())
                        .duration(searchOptions.getDuration())
                        .channel(searchOptions.getChannel())
                        .name(searchResult.getSnippet().getTitle())
                        .build();
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                                       + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
//
//    /*
//     * Prompt the user to enter a query term and return the user-specified term.
//     */
//    private static String getInputQuery() throws IOException {
//
//        String inputQuery = "";
//
//        System.out.print("Please enter a search term: ");
//        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
//        inputQuery = bReader.readLine();
//
//        if (inputQuery.length() < 1) {
//            // Use the string "YouTube Developers Live" as a default.
//            inputQuery = "YouTube Developers Live";
//        }
//        return inputQuery;
//    }

    /*
     * Prints out all results in the Iterator. For each result, print the
     * title, video ID, and thumbnail.
     *
     * @param iteratorSearchResults Iterator of SearchResults to print
     *
     * @param query Search query (String)
     */
//    private static void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {
//
//        System.out.println("\n=============================================================");
//        System.out.println(
//                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
//        System.out.println("=============================================================\n");
//
//        if (!iteratorSearchResults.hasNext()) {
//            System.out.println(" There aren't any results for your query.");
//        }
//
//        while (iteratorSearchResults.hasNext()) {
//
//            SearchResult singleVideo = iteratorSearchResults.next();
//            ResourceId rId = singleVideo.getId();
//
//            // Confirm that the result represents a video. Otherwise, the
//            // item will not contain a video ID.
//            if (rId.getKind().equals("youtube#video")) {
//                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
//
//                System.out.println(" Video Id:" + rId.getVideoId());
//                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
//                System.out.println(" Thumbnail: " + thumbnail.getUrl());
//                System.out.println("\n-------------------------------------------------------------\n");
//            }
//        }
//    }
}
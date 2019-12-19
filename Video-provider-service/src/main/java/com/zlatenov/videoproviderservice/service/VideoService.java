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
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GameDto;
import com.zlatenov.spoilerfreesportsapi.model.dto.game.GamesDto;
import com.zlatenov.videoproviderservice.auth.Auth;
import com.zlatenov.videoproviderservice.model.Channel;
import com.zlatenov.videoproviderservice.model.Game;
import com.zlatenov.videoproviderservice.model.SearchOptions;
import com.zlatenov.videoproviderservice.model.Video;
import com.zlatenov.videoproviderservice.transformer.ModelTransformer;
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
    private ModelTransformer modelTransformer;

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
    public List<Video> getVideosForGame(GameDto gameDtoe) {
        List<Channel> channels;
        try {
            channels = createListOfChannels();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        List<SearchOptions> searchOptions = null;
        searchOptions = createSearchOptionsForGame(modelTransformer.transformDtoToGame(gameDtoe), channels);

        return composeVideoList(searchOptions);
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

    private List<SearchOptions> createSearchOptionsForGame(Game game, List<Channel> channels) {
        List<SearchOptions> searchOptions = new ArrayList<>();
        channels.forEach(channel -> searchOptions.addAll(createSearchOptionForGame(channel, game)));
        return searchOptions;
    }

    private List<SearchOptions> createSearchOptionForGame(Channel channel, Game game) {
        List<SearchOptions> searchOptions = new ArrayList<>();
        String videoName = composeVideoName(game);
        channel.getDurations()
                .forEach(duration -> searchOptions.add(SearchOptions.builder()
                        .channel(channel.getId())
                        .date(game.getDate())
                        .videoName(videoName)
                        .homeTeamName(game.getHomeTeamName())
                        .awayTeamName(game.getAwayTeamName())
                        .duration(duration)
                        .build()));
        return searchOptions;
    }

    private String composeVideoName(Game game) {
        return game.getHomeTeamName() + " " + game.getAwayTeamName() + " highlights";
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
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search-sample").build();

            YouTube.Search.List search = youtube.search().list("id,snippet").setPublishedAfter(
                    new DateTime(searchOptions.getDate()));

            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(searchOptions.getVideoName());
            search.setVideoEmbeddable("true");

            search.setType("video");
            search.setVideoDuration(searchOptions.getDuration());
            search.setChannelId(searchOptions.getChannel()); //UC8ndn9yAGs5L8NqKUBKzfyw

            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (CollectionUtils.isNotEmpty(searchResultList)) {
                SearchResult searchResult = searchResultList.get(0);
                return Video.builder()
                        .id(searchResult.getId().getVideoId())
                        .duration(searchOptions.getDuration())
                        .channel(searchOptions.getChannel())
                        .homeTeamName(searchOptions.getHomeTeamName())
                        .awayTeamName(searchOptions.getAwayTeamName())
                        .name(searchResult.getSnippet().getTitle())
                        .date(searchOptions.getDate().toString())
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

    public List<Video> getVideos(GamesDto gamesDto) {
        return gamesDto.getGameDtos().stream()
                .flatMap(gameDto -> getVideosForGame(gameDto).stream())
                .collect(Collectors.toList());
    }
}
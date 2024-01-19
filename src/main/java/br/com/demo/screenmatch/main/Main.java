package br.com.demo.screenmatch.main;

import br.com.demo.screenmatch.model.Episode;
import br.com.demo.screenmatch.model.EpisodeData;
import br.com.demo.screenmatch.model.SeasonData;
import br.com.demo.screenmatch.model.SeriesData;
import br.com.demo.screenmatch.services.DataSerializer;
import br.com.demo.screenmatch.services.RequesterAPI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private Scanner reader = new Scanner(System.in);
    private RequesterAPI requesterAPI = new RequesterAPI();
    private DataSerializer dataSerializer = new DataSerializer();

    public void showMenu() {
        System.out.println("Insert a a series name to search...");
        var seriesName = reader.nextLine();
        var data = requesterAPI.getDataFromOmdbAPI(seriesName);
        SeriesData seriesData = dataSerializer.deserialize(data, SeriesData.class);
        System.out.println(seriesData);

        List<SeasonData> seasonDataList = new ArrayList<>();
        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            data = requesterAPI.getDataFromOmdbAPI(seriesName, i);
            SeasonData season = dataSerializer.deserialize(data, SeasonData.class);
            seasonDataList.add(season);
        }
//        seasonDataList.forEach(System.out::println);
        seasonDataList.forEach(season -> {
            System.out.println("Season " + season.number());
            season.episodes().stream().map(EpisodeData::title).forEach(System.out::println);
        });

        List<EpisodeData> episodeDataList = seasonDataList.stream()
                .flatMap(season -> season.episodes().stream())
                .collect(Collectors.toList());

        /*System.out.println("\n Top 10 Episodes:");

        episodeDataList.stream()
                .filter(episode -> !episode.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(10)
                .forEach(System.out::println);*/

        List<Episode> episodes = seasonDataList.stream()
                .flatMap(season -> season.episodes().stream()
                        .map(episodeData -> new Episode(season.number(), episodeData)))
                .collect(Collectors.toList());

        episodes.forEach(System.out::println);

        System.out.println("\n Enter the name of the episode you want to search:");
        var episodeName = reader.nextLine();

        Optional<Episode> foundEpisode = episodes.stream().filter(e -> e.getTitle().toLowerCase().contains(episodeName.toLowerCase()))
                .findFirst();

        if (foundEpisode.isPresent()) {
            System.out.println(foundEpisode.get());
        } else {
            System.out.println("Episode not found");
        }

        //medium rating per season
        Map<Integer, Double> mediumRatingPerSeason = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));

        System.out.println("\n Medium rating per season:");
        mediumRatingPerSeason.forEach((season, rating) -> System.out.println("Season " + season + ": " + rating));

        DoubleSummaryStatistics statistics = episodes.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));
        System.out.println("\n Statistics: " + statistics);
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        System.out.println("After which year do you want to see the episodes?");
//        var year = reader.nextInt();
//        reader.nextLine();
//
//        LocalDate dateAfterToFilter = LocalDate.of(year, 1, 1);
//        episodes.stream().filter(e -> e.getLaunchDate().isAfter(dateAfterToFilter))
//                .forEach(e -> {
//                    System.out.println(
//                            "Season: " + e.getSeason() +
//                                    " Title: " + e.getTitle() +
//                                    " Launch Date: " + e.getLaunchDate().format(dateTimeFormatter)
//                    );
//                });
    }

}

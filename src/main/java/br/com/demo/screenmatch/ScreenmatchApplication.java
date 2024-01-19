package br.com.demo.screenmatch;

import br.com.demo.screenmatch.main.Main;
import br.com.demo.screenmatch.model.EpisodeData;
import br.com.demo.screenmatch.model.SeasonData;
import br.com.demo.screenmatch.model.SeriesData;
import br.com.demo.screenmatch.services.DataSerializer;
import br.com.demo.screenmatch.services.RequesterAPI;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main m = new Main();
		m.showMenu();
	}
}

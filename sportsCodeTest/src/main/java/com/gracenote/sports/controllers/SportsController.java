package com.gracenote.sports.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.gracenote.sports.entities.GameStats;
import com.gracenote.sports.entities.Player;
import com.gracenote.sports.entities.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class SportsController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/populateTeams", method = RequestMethod.GET)
	public void addTeams() {
		try {
			jdbcTemplate.execute(
					"DROP TABLE IF EXISTS teams;CREATE TABLE teams AS SELECT * FROM CSVREAD('classpath:Teams.csv'); ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		jdbcTemplate.execute("");
	}
	
	@RequestMapping(value = "/populateGameResult", method = RequestMethod.GET)
	public void populateGameResult() {
		try {
			jdbcTemplate.execute(
					"DROP TABLE IF EXISTS games_result;CREATE TABLE games_result AS SELECT * FROM CSVREAD('classpath:Game_Results.csv'); ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/team/players/{teamName}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Player>> getPlayersByTeamId(@PathVariable String teamName)
	{
		System.out.println(teamName);
		List<Player> players = new ArrayList<Player>();
		String sql = "SELECT * FROM TEAMS WHERE Name = ?";
		players = jdbcTemplate.query(
				sql, new Object[] { teamName }, new PlayersRowMapper());
		
		HttpStatus status = players.size() != 0 ?
				HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<List<Player>>(players, status);
		
	}
	
	@RequestMapping(value = "/gamestats/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameStats>> getGameStats(@PathVariable String id)
	{
		System.out.println(id);
		List<GameStats> gameStats = new ArrayList<GameStats>();
		String sql = "select goals, league_name ,league_id, team_id, team_name from game_results where game_id=?";
		gameStats = jdbcTemplate.query(
				sql, new Object[] { id }, new GameStatsRowMapper());
		HttpStatus status = gameStats.size() != 0 ?
				HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<List<GameStats>>(gameStats, status);
		
	}
	
	
	@RequestMapping(value = "/gamestats/winner/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameStats>> getWinnerOfGane(@PathVariable String id)
	{
		System.out.println(id);
		List<GameStats> gameStats = new ArrayList<GameStats>();
		String sql = "select goals, league_name ,league_id, team_id, team_name from game_results where game_id=?";
		gameStats = jdbcTemplate.query(
				sql, new Object[] { id }, new GameStatsRowMapper());
		Optional<GameStats> winner =gameStats.stream().max((p1, p2) -> Integer.toString(p1.getGoals()).compareTo(Integer.toString(p2.getGoals())));
		HttpStatus status = gameStats.size() != 0 ?
				HttpStatus.OK : HttpStatus.NOT_FOUND;
		gameStats.clear();
		gameStats.add(winner.get());
		return new ResponseEntity<List<GameStats>>(gameStats, status);
		
	}
	
	public class GameStatsRowMapper implements RowMapper<GameStats>
	{
		public GameStats mapRow(ResultSet rs, int rowNum) throws SQLException {
		GameStats stats = new GameStats();
		stats.setLeagueName(rs.getString("league_name"));
		stats.setGoals(rs.getInt("goals"));
		stats.setLeagueId(rs.getInt("league_id"));
		Team team = new Team();
		team.setTeamID(rs.getInt("team_id"));
		team.setTeamName(rs.getString("team_name"));
		stats.setTeam(team);
		return stats;
		}
		
	}
	public class PlayersRowMapper implements RowMapper<Player>
	{
		public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
		Player player = new Player();
		player.setPlayerID(rs.getInt("Player_ID"));
		player.setFirstName(rs.getString("FIRSTNAME"));
		player.setLastName(rs.getString("LASTNAME"));
		return player;
		}
		
	}

	@RequestMapping("/docker")
	public String index() {
		System.out.println("gagan");
		return "Hello Docker World";
	}

}
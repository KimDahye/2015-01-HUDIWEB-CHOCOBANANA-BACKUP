package test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ubuntudo.controller.guild.GuildController;
import ubuntudo.model.GuildEntity;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/ubuntudo-servlet.xml")
public class GuildColtrollerTest {

	@Autowired
	private GuildController guildController;

	@Test
	public void insertNewGuildControllerTest() {
		long leaderId = 20l;
		String newGuildName = "The Guild20";
		assertEquals(2, guildController.insertNewGuildController(leaderId, newGuildName));
	}

	@Test
	public void insertUserToGuildControllerTest() {
		long guildId = 1l;
		long userId = 1l;
		assertEquals(1, guildController.insertUserToGuildController(guildId, userId));
	}

	@Test
	public void retrieveGuildSearchControllerTest() {
		String guildName = "";
		List<GuildEntity> guildList = guildController.retrieveGuildListSearchController(guildName);
		System.out.println(guildList.toString());

		Gson gson = new Gson();
		String json = gson.toJson(guildList);
		System.out.println(json);

		assertNotNull(guildList);
	}

	@Test
	public void updateGuildControllerTest() {
		long gid = 3l;
		long leaderId = 105l;
		String guildName = "Guild EDITED";
		String status = "0";

		assertEquals(1, guildController.updateGuildController(gid, leaderId, guildName, status));
	}
}
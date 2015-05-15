package ubuntudo.controller;

import java.sql.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ubuntudo.biz.TodoBiz;
import ubuntudo.model.TodoEntity;
import ubuntudo.model.UserEntity;

@Controller
@RequestMapping(value = "/todo")
public class TodoController {
	private static final Logger logger = LoggerFactory
			.getLogger(TodoController.class);

	@Autowired
	TodoBiz tbiz;

	@RequestMapping(value="/{tid}", method = RequestMethod.PUT)
	public @ResponseBody void updateTodoController(HttpSession session,
			long tid, String title_edit, String note_edit, String due_date_edit) {
		logger.debug("===>updatePersonalTodo");

		UserEntity user = (UserEntity) session.getAttribute("user");
		long uid = user.getUid();

		logger.debug("current user: " + uid);
		logger.debug("current todo: " + tid);
		logger.debug("new title_edit: " + title_edit);
		logger.debug("new note_edit: " + note_edit);
		logger.debug("new due_date_edit: " + due_date_edit);

		tbiz.updatePersonalTodoBiz(new TodoEntity(tid, null, title_edit, note_edit, due_date_edit, null, uid, null));
		logger.debug("<===updateTupdatePersonalTodoodo");
	}

	@RequestMapping(value="/{tid}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteTodoController(HttpSession session, long tid) {
		logger.debug("===>deletePersonalTodo");

		UserEntity user = (UserEntity) session.getAttribute("user");
		long uid = user.getUid();

		logger.debug("current user: " + uid);
		logger.debug("current todo: " + tid);
		
		tbiz.deletePersonalTodoBiz(new TodoEntity(tid, null, "", "", "", null, uid, null));
		logger.debug("<===deletePersonalTodo");
	}

	//투두 추가는 personal 페이지에서 뿐만 아니라 모든 페이지에서 일어날 수 있으므로 PersonalController가 아닌 TodoController에서 구현한다.
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody TodoEntity addPersonalTodo(HttpSession session,
			@RequestParam(value = "pid") Long pid,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "contents") String contents,
			@RequestParam(value = "dueDate") String duedate)
			throws ServletRequestBindingException {
		logger.debug("/todo POST요청에 대해 응답");

		if (session.getAttribute("user") == null) {
			logger.debug("/personal 요청에 대해 응답 - 세션이 정상적이지 않을때");
			// return "redirect:/start"; string으로 보내야한다.
			// 나중에 주기적으로 ajax요청 보낼 때 체크해서 redirect해주면 될 듯
			return null;
		}

		UserEntity user = (UserEntity) session.getAttribute("user");
		Long uid = user.getUid();
		Date due = Date.valueOf(duedate); // duedate가 null값이거나 "-"가 없으면 error난다. 
		logger.debug("param check: uid={}, pid={}, title={}, contents={}, due={}", uid, pid, title, contents, due);
		TodoEntity newTodo = new TodoEntity(pid, title, contents, duedate, uid);
		TodoEntity resultTodo = tbiz.addTodo(newTodo);
		return resultTodo;
	}
}
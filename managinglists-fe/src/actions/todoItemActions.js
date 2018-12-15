import axios from "axios";
import {
  GET_TODO_ITEMS,
  GET_TODO_ITEM,
  DELETE_TODO_ITEM,
  GET_ERRORS
} from "./types";

export const addTodoItem = (topicId, todoItem, history) => async dispatch => {
  try {
    await axios.post(`/topic/${topicId}/todoitem`, todoItem);
    history.push(`/topicBoard/${topicId}`);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const addTodoItemAttachments = (
  todoItem,
  files,
  topicId,
  history
) => async dispatch => {
  const config = {
    headers: {
      "content-type": "multipart/form-data"
    }
  };

  const formDataFiles = new FormData();
  //formDataFiles.set("todoItem", todoItem);
  formDataFiles.append(
    "todoItem",
    new Blob([JSON.stringify(todoItem)], {
      type: "application/json"
    })
  );
  for (const file of files) {
    formDataFiles.append("files", file);
  }

  try {
    await axios.post(
      `/topic/${topicId}/todoitem/attachments`,
      formDataFiles
      //config
    );
    history.push(`/topicBoard/${topicId}`);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const addTodoItemAttachments2 = (
  todoItem,
  files,
  topicId,
  history
) => async dispatch => {
  const config = {
    headers: {
      "content-type": "multipart/form-data"
    }
  };

  const formDataFiles = new FormData();
  for (const file of files) {
    formDataFiles.append("files", file);
  }

  try {
    await axios.post(`/attachment/uploadMultipleFiles`, formDataFiles, config);
    history.push(`/topicBoard/${topicId}`);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const updateTodoItem = (
  todoItem,
  files,
  topicId,
  todoItemId,
  history
) => async dispatch => {
  const config = {
    headers: {
      "content-type": "multipart/form-data"
    }
  };

  const formDataFiles = new FormData();
  //formDataFiles.set("todoItem", todoItem);
  formDataFiles.append(
    "todoItem",
    new Blob([JSON.stringify(todoItem)], {
      type: "application/json"
    })
  );
  for (const file of files) {
    formDataFiles.append("files", file);
  }

  try {
    await axios.patch(
      `/topic/${topicId}/todoitem/${todoItemId}/attachments`,
      formDataFiles
      //config
    );
    history.push(`/topicBoard/${topicId}`);
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const getTodoItems = topicId => async dispatch => {
  try {
    const res = await axios.get(`/topic/${topicId}/todoitem`);
    dispatch({
      type: GET_TODO_ITEMS,
      payload: res.data
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const getTodoItem = (topicId, todoItemId, history) => async dispatch => {
  try {
    const res = await axios.get(`/topic/${topicId}/todoitem/${todoItemId}`);
    dispatch({
      type: GET_TODO_ITEM,
      payload: res.data
    });
  } catch (err) {
    history.push(`/dashboard`);
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const deleteTodoItem = (topicId, todoItemId) => async dispatch => {
  await axios.delete(`/topic/${topicId}/todoitem/${todoItemId}`);
  dispatch({
    type: DELETE_TODO_ITEM,
    payload: todoItemId
  });
};

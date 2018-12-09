import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import topicReducer from "./topicReducer";
import todoItemReducer from "./todoItemReducer";

export default combineReducers({
  errors: errorReducer,
  appTopic: topicReducer,
  appTodoItem: todoItemReducer
});

import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import topicReducer from "./topicReducer";

export default combineReducers({
  errors: errorReducer,
  appTopic: topicReducer
});

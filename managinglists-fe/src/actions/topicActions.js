import axios from "axios";
import { GET_ERRORS, GET_TOPICS, GET_TOPIC, DELETE_TOPIC } from "./types";

export const createTopic = (topic, history) => async dispatch => {
  try {
    await axios.post("/topic", topic);
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const updateTopic = (id, topic, history) => async dispatch => {
  try {
    await axios.patch(`/topic/${id}`, topic);
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data
    });
  }
};

export const getTopics = () => async dispatch => {
  const res = await axios.get("/topic/all");
  dispatch({
    type: GET_TOPICS,
    payload: res.data
  });
};

export const getTopic = (id, history) => async dispatch => {
  try {
    const res = await axios.get(`/topic/${id}`);
    dispatch({
      type: GET_TOPIC,
      payload: res.data
    });
  } catch (err) {
    history.push("/dashboard");
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const deleteTopic = id => async dispatch => {
  await axios.delete(`/topic/${id}`);
  dispatch({
    type: DELETE_TOPIC,
    payload: id
  });
};

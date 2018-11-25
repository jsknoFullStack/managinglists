import { GET_TOPICS, GET_TOPIC, DELETE_TOPIC } from "../actions/types";

const initialState = {
  topics: [],
  topic: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_TOPICS:
      return {
        ...state,
        topics: action.payload
      };

    case GET_TOPIC:
      return {
        ...state,
        topic: action.payload
      };

    case DELETE_TOPIC:
      return {
        ...state,
        topics: state.topics.filter(
          topic => topic.topicIdentifier !== action.payload
        )
      };

    default:
      return state;
  }
}

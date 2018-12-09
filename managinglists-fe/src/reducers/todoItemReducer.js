import {
  GET_TODO_ITEMS,
  GET_TODO_ITEM,
  DELETE_TODO_ITEM
} from "../actions/types";

const initialState = {
  todoItems: [],
  todoItem: {}
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_TODO_ITEMS:
      return {
        ...state,
        todoItems: action.payload
      };

    case GET_TODO_ITEM:
      return {
        ...state,
        todoItem: action.payload
      };

    case DELETE_TODO_ITEM:
      return {
        ...state,
        todoItems: state.todoItems.filter(
          todoItem => todoItem.id !== action.payload
        )
      };

    default:
      return state;
  }
}

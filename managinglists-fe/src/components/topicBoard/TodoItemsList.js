import React, { Component } from "react";
import TodoItem from "./todoItems/TodoItem";

class TodoItemsList extends Component {
  createTodoItemsList(todoItems) {
    return todoItems.map(todoItem => (
      <TodoItem key={todoItem.id} todoItem={todoItem} />
    ));
  }

  render() {
    const { todoItems } = this.props;
    const topicName = todoItems[0].topic.name;

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-12">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>Todo Items For Topic {topicName}</h3>
              </div>
            </div>
            {this.createTodoItemsList(todoItems)}
          </div>
        </div>
      </div>
    );
  }
}

export default TodoItemsList;

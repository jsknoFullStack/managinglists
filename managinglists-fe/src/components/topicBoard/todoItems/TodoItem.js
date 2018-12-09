import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { deleteTodoItem } from "../../../actions/todoItemActions";

class TodoItem extends Component {
  onDeleteClick = (topicId, todoItemId) => {
    this.props.deleteTodoItem(topicId, todoItemId);
  };

  render() {
    const { todoItem } = this.props;
    const topicId = todoItem.topic.id;

    return (
      <div className="card mb-1 bg-light">
        <div className={`card-header text-primary`}>ID: {todoItem.id}</div>
        <div className="card-body bg-light">
          <div className="row">
            <div className="col-md-8">
              <h5 className="card-title">{todoItem.element}</h5>
            </div>
            <div className="col-md-2">
              <Link
                to={`/topic/${topicId}/viewTodoItem/${todoItem.id}`}
                className="btn btn-primary"
              >
                View / Update
              </Link>
            </div>
            <div className="col-md-2">
              <button
                className="btn btn-danger ml-4"
                onClick={this.onDeleteClick.bind(this, topicId, todoItem.id)}
              >
                Delete
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

TodoItem.propTypes = {
  deleteTodoItem: PropTypes.func.isRequired
};

export default connect(
  null,
  { deleteTodoItem }
)(TodoItem);

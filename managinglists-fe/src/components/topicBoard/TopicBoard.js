import React, { Component } from "react";
import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { getTodoItems } from "../../actions/todoItemActions";
import TodoItemsList from "./TodoItemsList";

class TopicBoard extends Component {
  constructor() {
    super();
    this.state = {
      errors: {}
    };
  }

  componentDidMount() {
    const { topicId } = this.props.match.params;
    this.props.getTodoItems(topicId);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  boardAlgorithm(errors, todoItems) {
    if (todoItems.length < 1) {
      if (errors.topicNotFound) {
        return (
          <div className="alert alert-danger text-center" role="alert">
            {errors.topicNotFound}
          </div>
        );
      } else if (errors.topicIdentifier) {
        return (
          <div className="alert alert-danger text-center" role="alert">
            {errors.topicIdentifier}
          </div>
        );
      } else {
        return (
          <div className="alert alert-info text-center" role="alert">
            No Project Tasks on this board
          </div>
        );
      }
    } else {
      return <TodoItemsList todoItems={todoItems} />;
    }
  }

  render() {
    const { topicId } = this.props.match.params;
    const { todoItems } = this.props;
    const { errors } = this.state;

    const boardContent = this.boardAlgorithm(errors, todoItems);

    return (
      <div className="container">
        <Link to={`/dashboard`} className="btn btn-light">
          Back to Dashboard
        </Link>
        <Link to={`/topic/${topicId}/addTodoItem`} className="btn btn-light">
          Create TodoItem
        </Link>
        <br />
        <hr />
        {boardContent}
      </div>
    );
  }
}

TopicBoard.propTypes = {
  todoItems: PropTypes.array.isRequired,
  getTodoItems: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  todoItems: state.appTodoItem.todoItems,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getTodoItems }
)(TopicBoard);

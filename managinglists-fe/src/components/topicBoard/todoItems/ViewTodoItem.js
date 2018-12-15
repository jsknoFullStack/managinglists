import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { getTodoItem } from "../../../actions/todoItemActions";
import classnames from "classnames";
import { Link } from "react-router-dom";
import AttachmentsTable from "../../table/AttachmentsTable";
import AttachmentsTableView from "../../table/AttachmentsTableView";

class ViewTodoItem extends Component {
  constructor(props) {
    super(props);
    const { topicId, todoItemId } = this.props.match.params;

    this.state = {
      id: todoItemId,
      element: "",
      notes: "",
      attachments: [],
      topic: {
        id: topicId
      },
      errors: {},
      allAttachments: []
    };
  }

  componentDidMount() {
    const { topicId, todoItemId } = this.props.match.params;
    this.props.getTodoItem(topicId, todoItemId, this.props.history);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    const { id, element, notes, attachments, topic } = nextProps.todoItem;

    const allAttachments = attachments.slice();
    allAttachments.forEach(function(attachment, index) {
      attachment.num = ++index;
      attachment.key = attachment.filename + attachment.size;
    });

    this.setState({
      id: id,
      element: element,
      notes: notes,
      attachments: attachments,
      topic: topic,
      filesToUpload: [],
      allAttachments: allAttachments
    });
  }

  render() {
    const { topicId } = this.props.match.params;
    const { errors } = this.state;
    const columns = [
      {
        dataField: "num",
        text: "File Number"
      },
      {
        dataField: "filename",
        text: "File Name"
      },
      {
        dataField: "size",
        text: "File Size"
      }
    ];

    return (
      <div className="add-PBI">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <Link to={`/topicBoard/${topicId}`} className="btn btn-light">
                Back to Topic Board
              </Link>
              <h4 className="display-4 text-center">
                Topic Name: {this.state.topic.name}
              </h4>
              <p className="lead text-center">Update Todo Item</p>
              <form>
                <div className="form-group">
                  <input
                    readOnly
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.element
                    })}
                    name="element"
                    placeholder="Topic Element"
                    value={this.state.element}
                  />
                  {errors.element && (
                    <div className="invalid-feedback">{errors.element}</div>
                  )}
                </div>
                <div className="form-group">
                  <textarea
                    readOnly
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.notes
                    })}
                    placeholder="TodoItem Notes"
                    name="notes"
                    value={this.state.notes}
                  />
                  {errors.notes && (
                    <div className="invalid-feedback">{errors.notes}</div>
                  )}
                </div>
                <h6>Attachments</h6>
                <AttachmentsTableView
                  keyField="key"
                  dataRows={this.state.allAttachments}
                  dataColumns={columns}
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

ViewTodoItem.propTypes = {
  getTodoItem: PropTypes.func.isRequired,
  todoItem: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  todoItem: state.appTodoItem.todoItem,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getTodoItem }
)(ViewTodoItem);

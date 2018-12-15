import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { getTodoItem, updateTodoItem } from "../../../actions/todoItemActions";
import classnames from "classnames";
import { Link } from "react-router-dom";
import AttachmentsTable from "../../table/AttachmentsTable";

class UpdateTodoItem extends Component {
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
      filesToUpload: [],
      allAttachments: []
    };

    this.onChange = this.onChange.bind(this);
    this.onAttachmentsChange = this.onAttachmentsChange.bind(this);
    this.onRemoveFile = this.onRemoveFile.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
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

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onAttachmentsChange(e) {
    let index = this.state.allAttachments.length;
    const newFiles = e.target.files;
    const attachmentsFromFiles = [];
    for (const file of newFiles) {
      const attachmentFromFile = {
        num: ++index,
        filename: file.name,
        size: file.size,
        key: file.name + file.size
      };
      attachmentsFromFiles.push(attachmentFromFile);
    }
    this.setState(prevState => ({
      filesToUpload: [...prevState.filesToUpload, ...newFiles],
      allAttachments: [...prevState.allAttachments, ...attachmentsFromFiles]
    }));
  }

  onRemoveFile(attachmentToRemove) {
    if (attachmentToRemove.id) {
      this.setState(prevState => ({
        attachments: prevState.attachments.filter(
          attachment => attachment.id !== attachmentToRemove.id
        )
      }));
    } else {
      this.setState(prevState => ({
        filesToUpload: prevState.filesToUpload.filter(
          file =>
            file.name + file.size !==
            attachmentToRemove.filename + attachmentToRemove.size
        )
      }));
    }

    const modifiedAllAttachments = this.state.allAttachments.filter(
      attachment =>
        attachment.filename + attachment.size !==
        attachmentToRemove.filename + attachmentToRemove.size
    );
    modifiedAllAttachments.forEach(function(attachment, index) {
      attachment.num = ++index;
    });

    this.setState({ allAttachments: modifiedAllAttachments });
  }

  onSubmit(e) {
    e.preventDefault();

    const updatedTodoItem = {
      id: this.state.id,
      element: this.state.element,
      notes: this.state.notes,
      attachments: this.state.attachments,
      topic: {
        id: this.state.topic.id
      }
    };

    this.props.updateTodoItem(
      updatedTodoItem,
      this.state.filesToUpload,
      this.state.topic.id,
      this.state.id,
      this.props.history
    );
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
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.element
                    })}
                    name="element"
                    placeholder="Topic Element"
                    value={this.state.element}
                    onChange={this.onChange}
                  />
                  {errors.element && (
                    <div className="invalid-feedback">{errors.element}</div>
                  )}
                </div>
                <div className="form-group">
                  <textarea
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.notes
                    })}
                    placeholder="TodoItem Notes"
                    name="notes"
                    value={this.state.notes}
                    onChange={this.onChange}
                  />
                  {errors.notes && (
                    <div className="invalid-feedback">{errors.notes}</div>
                  )}
                </div>
                <h6>Attachments</h6>
                <div className="form-group">
                  <input
                    type="file"
                    multiple
                    className={classnames("form-control form-control-lg ", {
                      "is-invalid": errors.attachments
                    })}
                    name="allAttachments"
                    onChange={this.onAttachmentsChange}
                  />
                  {errors.allAttachments && (
                    <div className="invalid-feedback">
                      {errors.allAttachments}
                    </div>
                  )}
                </div>
                <AttachmentsTable
                  keyField="key"
                  dataRows={this.state.allAttachments}
                  dataColumns={columns}
                  handleClick={this.onRemoveFile}
                />

                <input
                  type="submit"
                  className="btn btn-primary btn-block mt-4"
                />
              </form>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

UpdateTodoItem.propTypes = {
  getTodoItem: PropTypes.func.isRequired,
  todoItem: PropTypes.object.isRequired,
  updateTodoItem: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  todoItem: state.appTodoItem.todoItem,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getTodoItem, updateTodoItem }
)(UpdateTodoItem);

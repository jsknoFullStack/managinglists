import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import classnames from "classnames";
import { getTopic } from "../../../actions/topicActions";
import {
  addTodoItem,
  addTodoItemAttachments,
  addTodoItemAttachments2
} from "../../../actions/todoItemActions";
import PropTypes from "prop-types";
import AttachmentsTable from "../../table/AttachmentsTable";

class AddTodoItem extends Component {
  constructor(props) {
    super(props);
    const { topicId } = this.props.match.params;

    this.state = {
      element: "",
      notes: "",
      attachments: [],
      topic: {
        id: topicId
      },
      errors: {},
      filesToUpload: []
    };

    this.onChange = this.onChange.bind(this);
    this.onAttachmentsChange = this.onAttachmentsChange.bind(this);
    this.onRemoveFile = this.onRemoveFile.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentDidMount() {
    const { topicId } = this.props.match.params;
    this.props.getTopic(topicId, this.props.history);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    const { topic } = nextProps;
    this.setState({ topic: topic });
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onAttachmentsChange(e) {
    let index = this.state.attachments.length + 1;
    const newFiles = e.target.files;
    const attachmentsFromFiles = [];
    for (const file of newFiles) {
      const attachmentFromFile = {
        num: index++,
        filename: file.name,
        size: file.size,
        key: file.name + file.size
      };
      attachmentsFromFiles.push(attachmentFromFile);
    }
    this.setState(prevState => ({
      filesToUpload: [...prevState.filesToUpload, ...newFiles],
      attachments: [...prevState.attachments, ...attachmentsFromFiles]
    }));
  }

  onRemoveFile(attachmentToRemove) {
    const modifiedAttachments = this.state.attachments.filter(
      attachment =>
        attachment.filename + attachment.size !==
        attachmentToRemove.filename + attachmentToRemove.size
    );
    modifiedAttachments.forEach(function(attachment, index) {
      attachment.num = ++index;
    });
    this.setState(prevState => ({
      filesToUpload: prevState.filesToUpload.filter(
        file =>
          file.name + file.size !==
          attachmentToRemove.filename + attachmentToRemove.size
      ),
      attachments: modifiedAttachments
    }));
  }

  onSubmit(e) {
    e.preventDefault();

    const newTodoItem = {
      element: this.state.element,
      notes: this.state.notes,
      topic: {
        id: this.state.topic.id
      }
    };

    this.props.addTodoItemAttachments(
      newTodoItem,
      this.state.filesToUpload,
      this.state.topic.id,
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
              <p className="lead text-center">Add Todo Item</p>
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
                    name="attachments"
                    onChange={this.onAttachmentsChange}
                  />
                  {errors.attachments && (
                    <div className="invalid-feedback">{errors.attachments}</div>
                  )}
                </div>
                <AttachmentsTable
                  keyField="key"
                  dataRows={this.state.attachments}
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

AddTodoItem.propTypes = {
  getTopic: PropTypes.func.isRequired,
  topic: PropTypes.object.isRequired,
  addTodoItem: PropTypes.func.isRequired,
  addTodoItemAttachments: PropTypes.func.isRequired,
  addTodoItemAttachments2: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  topic: state.appTopic.topic,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getTopic, addTodoItem, addTodoItemAttachments, addTodoItemAttachments2 }
)(AddTodoItem);

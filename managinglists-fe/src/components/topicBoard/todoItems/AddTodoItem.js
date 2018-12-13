import React, { Component } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import classnames from "classnames";
import {
  addTodoItem,
  addTodoItemAttachments,
  addTodoItemAttachments2
} from "../../../actions/todoItemActions";
import PropTypes from "prop-types";
import BootstrapTable from "react-bootstrap-table-next";

class AddTodoItem extends Component {
  constructor(props) {
    super(props);
    const { topicId } = this.props.match.params;

    this.state = {
      element: "",
      notes: "",
      attachments: [],
      topic: {
        id: topicId,
        name: ""
      },
      errors: {},
      filesToUpload: []
    };

    this.onChange = this.onChange.bind(this);
    this.onAttachmentsChange = this.onAttachmentsChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onAttachmentsChange(e) {
    let index = this.state.attachments.length;
    const files = e.target.files;
    const attachmentsFromFiles = [];
    for (var file of files) {
      const attachmentFromFile = {
        id: index++,
        name: file.name,
        size: file.size
      };
      attachmentsFromFiles.push(attachmentFromFile);
    }
    this.setState(prevState => ({
      filesToUpload: [...prevState.filesToUpload, ...files],
      attachments: [...prevState.attachments, ...attachmentsFromFiles]
    }));
  }

  onSubmit(e) {
    e.preventDefault();

    const newTodoItem = {
      element: this.state.element,
      notes: this.state.notes,
      attachments: this.state.attachments,
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
        dataField: "id",
        text: "File Number"
      },
      {
        dataField: "name",
        text: "File Name"
      },
      {
        dataField: "size",
        text: "File Size"
      },
      { dataField: "", text: "" }
    ];

    return (
      <div className="add-PBI">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <Link to={`/topicBoard/${topicId}`} className="btn btn-light">
                Back to Topic Board
              </Link>
              <h4 className="display-4 text-center">Add Todo Item</h4>
              <p className="lead text-center">
                Topic Name: {this.state.topic.name}
              </p>
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
                    //value={this.state.attachments}
                    onChange={this.onAttachmentsChange}
                  />
                  {errors.attachments && (
                    <div className="invalid-feedback">{errors.attachments}</div>
                  )}
                </div>
                <BootstrapTable
                  keyField="id"
                  data={this.state.attachments}
                  columns={columns}
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
  addTodoItem: PropTypes.func.isRequired,
  addTodoItemAttachments: PropTypes.func.isRequired,
  addTodoItemAttachments2: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { addTodoItem, addTodoItemAttachments, addTodoItemAttachments2 }
)(AddTodoItem);

import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { getTodoItem, updateTodoItem } from "../../../actions/todoItemActions";
import classnames from "classnames";
import { Link } from "react-router-dom";
import BootstrapTable from "react-bootstrap-table-next";

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
        id: topicId,
        name: ""
      },
      errors: {},
      filesToUpload: [],
      convertedFilesToUpload: []
    };

    this.onChange = this.onChange.bind(this);
    this.onAttachementsChange = this.onAttachementsChange.bind(this);
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

    this.setState({
      id: id,
      element: element,
      notes: notes,
      attachments: attachments,
      topic: topic,
      filesToUpload: [],
      convertedFilesToUpload: []
    });
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  onAttachementsChange(e) {
    let index = this.state.convertedFilesToUpload.length;
    const files = e.target.files;
    const convertedFiles = [];
    for (const file of files) {
      const convertedFile = {
        id: index++,
        name: file.name,
        size: file.size
      };
      convertedFiles.push(convertedFile);
    }
    this.setState(prevState => ({
      filesToUpload: [...prevState.filesToUpload, ...files],
      convertedFilesToUpload: [
        ...prevState.convertedFilesToUpload,
        ...convertedFiles
      ]
    }));
  }

  onSubmit(e) {
    e.preventDefault();
    const newTodoItem = {
      id: this.state.id,
      element: this.state.element,
      notes: this.state.notes,
      attachments: this.state.attachments,
      topic: {
        id: this.state.topic.id
      }
    };
    const files = new FormData();
    files.append("file", this.state.attachments);
    files.append("name", "fileName");
    this.props.updateTodoItem(
      this.state.topic.id,
      this.state.id,
      newTodoItem,
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
              <h4 className="display-4 text-center">View/Update Todo Item</h4>
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
                    value={this.state.attachments}
                    onChange={this.onAttachmentsChange}
                  />
                  {errors.attachments && (
                    <div className="invalid-feedback">{errors.attachments}</div>
                  )}
                </div>
                <BootstrapTable
                  keyField="id"
                  data={this.state.convertedFilesToUpload}
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

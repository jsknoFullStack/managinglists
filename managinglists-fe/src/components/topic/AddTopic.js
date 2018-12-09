import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { createTopic } from "../../actions/topicActions";
import classnames from "classnames";
import { Link } from "react-router-dom";

class AddTopic extends Component {
  constructor() {
    super();
    this.state = {
      topic: {
        name: "",
        description: ""
      },
      errors: {}
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  onChange(e) {
    const fieldName = e.target.name;
    const fieldValue = e.target.value;
    this.setState(prevState => ({
      topic: {
        ...prevState.topic,
        [fieldName]: fieldValue
      }
    }));
  }

  onSubmit(e) {
    e.preventDefault();
    this.props.createTopic(this.state.topic, this.props.history);
  }

  render() {
    const { errors } = this.state;
    const { topic } = this.state;

    return (
      <div>
        <div className="topic">
          <div className="container">
            <div className="row">
              <div className="col-md-8 m-auto">
                <Link to="/dashboard" className="btn btn-light">
                  Back to Dashboard
                </Link>
                <h5 className="display-4 text-center">Create Topic form</h5>
                <hr />
                <form onSubmit={this.onSubmit}>
                  <div className="form-group">
                    <input
                      type="text"
                      className={classnames("form-control form-control-lg ", {
                        "is-invalid": errors.name
                      })}
                      placeholder="Topic Name"
                      name="name"
                      value={topic.name}
                      onChange={this.onChange}
                    />
                    {errors.name && (
                      <div className="invalid-feedback">{errors.name}</div>
                    )}
                  </div>
                  <div className="form-group">
                    <textarea
                      className={classnames("form-control form-control-lg ", {
                        "is-invalid": errors.description
                      })}
                      placeholder="Topic Description"
                      name="description"
                      value={topic.description}
                      onChange={this.onChange}
                    />
                    {errors.description && (
                      <div className="invalid-feedback">
                        {errors.description}
                      </div>
                    )}
                  </div>

                  <input
                    type="submit"
                    className="btn btn-primary btn-block mt-4"
                  />
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

AddTopic.propTypes = {
  createTopic: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { createTopic }
)(AddTopic);

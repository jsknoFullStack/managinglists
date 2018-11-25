import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { createTopic, getTopic } from "../../actions/topicActions";
import classnames from "classnames";

class UpdateTopic extends Component {
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

  componentDidMount() {
    const { name } = this.props.match.params;
    this.props.getTopic(name, this.props.history);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    const { topic } = nextProps;
    this.setState({ topic: topic });
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
                <h5 className="display-4 text-center">Update Topic form</h5>
                <hr />
                <form onSubmit={this.onSubmit}>
                  <div className="form-group">
                    <input
                      type="text"
                      className={classnames("form-control form-control-lg ", {
                        "is-invalid": errors.name
                      })}
                      placeholder="Tpoic Name"
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

UpdateTopic.propTypes = {
  getTopic: PropTypes.func.isRequired,
  topic: PropTypes.object.isRequired,
  createTopic: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  topic: state.appTopic.topic,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { getTopic, createTopic }
)(UpdateTopic);

import React, { Component } from "react";
import { connect } from "react-redux";
import { getTopics } from "../actions/topicActions";
import PropTypes from "prop-types";
import CreateTopicButton from "./topic/CreateTopicButton";
import TopicItem from "./topic/TopicItem";

class Dashboard extends Component {
  componentDidMount() {
    this.props.getTopics();
  }

  render() {
    const { topics } = this.props;
    return (
      <div className="topics">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Topics</h1>
              <br />
              <CreateTopicButton />
              <br />
              <hr />
              {topics.map(topic => (
                <TopicItem key={topic.id} topic={topic} />
              ))}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

Dashboard.propTypes = {
  topics: PropTypes.array.isRequired,
  getTopics: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  topics: state.appTopic.topics
});

export default connect(
  mapStateToProps,
  { getTopics }
)(Dashboard);

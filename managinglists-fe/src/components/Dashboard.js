import React, { Component } from "react";
import { connect } from "react-redux";
import { getTopics, deleteTopic } from "../actions/topicActions";
import PropTypes from "prop-types";
import CreateTopicButton from "./topic/CreateTopicButton";
import TopicItem from "./topic/TopicItem";
import Modal from "./popup/Modal";

class Dashboard extends Component {
  constructor() {
    super();
    this.state = { isModalOpen: false };
  }
  componentDidMount() {
    this.props.getTopics();
  }

  openModal(topicId) {
    this.setState({
      isModalOpen: true,
      topicToDelete: topicId
    });
  }

  closeModal() {
    this.setState({
      isModalOpen: false,
      topicToDelete: null
    });
  }

  deleteTopicByName() {
    this.props.deleteTopic(this.state.topicToDelete);
    this.closeModal();
  }

  render() {
    const { topics } = this.props;
    return (
      <div className="topics">
        <div className="container">
          <Modal
            isOpen={this.state.isModalOpen}
            onClose={() => this.closeModal()}
            onConfirm={e => this.deleteTopicByName(e)}
          />
          <div className="row">
            <div className="col-md-12">
              <h1 className="display-4 text-center">Topics</h1>
              <br />
              <CreateTopicButton />
              <br />
              <hr />
              {topics.map(topic => (
                <TopicItem
                  key={topic.id}
                  topic={topic}
                  deleteTopic={e => this.openModal(e)}
                />
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
  { getTopics, deleteTopic }
)(Dashboard);

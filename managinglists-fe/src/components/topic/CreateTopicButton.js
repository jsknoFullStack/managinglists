import React from "react";
import { Link } from "react-router-dom";

const CreateTopicButton = () => {
  return (
    <React.Fragment>
      <Link to="/addTopic" className="btn btn-lg btn-info">
        Create a Topic
      </Link>
    </React.Fragment>
  );
};

export default CreateTopicButton;

import React, { Component } from "react";
import ReactDOM from "react-dom";
import PropTypes from "prop-types";

class Modal extends Component {
  render() {
    // Render nothing if the "show" prop is false
    if (!this.props.isOpen) {
      return null;
    }

    let modalStyle = {
      position: "absolute",
      top: "50%",
      left: "50%",
      width: "50%",
      transform: "translate(-50%, -50%)",
      zIndex: "2",
      background: "#fff"
    };

    let backdropStyle = {
      zIndex: "1",
      position: "fixed",
      top: 0,
      bottom: 0,
      left: 0,
      right: 0,
      backgroundColor: "rgba(0,0,0,0.3)"
    };

    return ReactDOM.createPortal(
      <div>
        <div style={modalStyle}>
          <div className="modal-content">
            <div className="modal-body">
              <strong>Could you confirm deletion of topic, please?</strong>
            </div>

            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-default"
                onClick={e => this.confirm(e)}
              >
                Confirm
              </button>
              <button
                type="button"
                className="btn btn-default"
                onClick={e => this.close(e)}
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
        <div style={backdropStyle} />
      </div>,
      document.querySelector("#deleteTopicModal")
    );
  }

  close(e) {
    e.preventDefault();

    if (this.props.onClose) {
      this.props.onClose();
    }
  }

  confirm(e) {
    e.preventDefault();

    if (this.props.onConfirm) {
      this.props.onConfirm(e);
    }
  }
}

Modal.propTypes = {
  onClose: PropTypes.func.isRequired,
  onConfirm: PropTypes.func.isRequired,
  isOpen: PropTypes.bool
};

export default Modal;

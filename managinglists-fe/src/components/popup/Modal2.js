import React from "react";
import ReactDOM from "react-dom";

const Modal2 = props => {
  const backdropStyle = {
    position: "fixed",
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
    backgroundColor: "rgba(0,0,0,0.3)",
    padding: 50
  };

  // The modal "window"
  const modalStyle = {
    backgroundColor: "#fff",
    borderRadius: 5,
    maxWidth: 500,
    minHeight: 300,
    margin: "0 auto",
    padding: 30
  };

  return ReactDOM.createPortal(
    <div className="backdrop" style={{ backdropStyle }}>
      <div className="" style={{ modalStyle }}>
        {props.children}

        <div className="footer">
          <button onClick={props.onClose}>Close</button>
        </div>
      </div>
    </div>,
    document.querySelector("#modal")
  );
};

export default Modal2;

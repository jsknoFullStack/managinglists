import React, { Component } from "react";
import { Provider } from "react-redux";
import { BrowserRouter as Router, Route } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import logo from "./logo.svg";
import "./App.css";
import store from "./store";
import Header from "./components/layout/Header";
import Dashboard from "./components/Dashboard";
import AddTopic from "./components/topic/AddTopic";
import UpdateTopic from "./components/topic/UpdateTopic";
import TopicBoard from "./components/topicBoard/TopicBoard";
import AddTodoItem from "./components/topicBoard/todoItems/AddTodoItem";
import UpdateTodoItem from "./components/topicBoard/todoItems/UpdateTodoItem";
import ViewTodoItem from "./components/topicBoard/todoItems/ViewTodoItem";

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Header />
            <Route exact path="/dashboard" component={Dashboard} />
            <Route exact path="/addTopic" component={AddTopic} />
            <Route exact path="/updateTopic/:id" component={UpdateTopic} />
            <Route exact path="/topicBoard/:topicId" component={TopicBoard} />
            <Route
              exact
              path="/topic/:topicId/addTodoItem"
              component={AddTodoItem}
            />
            <Route
              exact
              path="/topic/:topicId/updateTodoItem/:todoItemId"
              component={UpdateTodoItem}
            />
            <Route
              exact
              path="/topic/:topicId/viewTodoItem/:todoItemId"
              component={ViewTodoItem}
            />
          </div>
        </Router>
      </Provider>
    );
  }
}

export default App;

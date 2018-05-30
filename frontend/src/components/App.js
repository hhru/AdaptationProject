import React from 'react';
import ReactDOM from 'react-dom';
import { Switch, BrowserRouter as Router, Route } from 'react-router-dom';
import { connect } from 'react-redux';
import { Container } from 'reactstrap';

import AddEmployee from './AddEmployee';
import EditEmployee from './EditEmployee';
import AddTask from './tasks/AddTask';
import Questionnaire from './questionnaire/Questionnaire';
import QuestionnaireResult from './questionnaire/QuestionnaireResult';
import ListEmployees from './ListEmployees';
import EmployeePage from './EmployeePage';
import HomePage from './HomePage';
import NotFound from './NotFound';
import NotAuthorized from './NotAuthorized';
import Header from './Header';
import Donate from './Donate';

const mapStateToProps = (state) => {
  return { loggedIn: state.loggedIn };
};

class App extends React.Component {
  constructor(props) {
    super(props);

    this.handleLoginState = this.handleLoginState.bind(this);
  }

  handleLoginState(component) {
    return this.props.loggedIn ? component : NotAuthorized;
  }

  render() {
    return (
      <div>
        <Switch>
          <Route exact path="/questionnaire/:id" />
          <Route component={Header} />
        </Switch>
        <Container>
          <Switch>
            <Route exact path="/" component={HomePage} />
            <Route exact path="/add_employee" component={this.handleLoginState(AddEmployee)} />
            <Route
              exact
              path="/edit_employee/:id"
              component={this.handleLoginState(EditEmployee)}
            />
            <Route exact path="/list_employees" component={this.handleLoginState(ListEmployees)} />
            <Route exact path="/employee/:id" component={this.handleLoginState(EmployeePage)} />
            <Route
              exact
              path="/employee/:id/questionnaire"
              component={this.handleLoginState(QuestionnaireResult)}
            />
            <Route exact path="/add_tasks/:id" component={AddTask} />
            <Route exact path="/questionnaire/:id" component={Questionnaire} />
            <Route exact path="/donate" component={Donate} />
            <Route component={NotFound} />
          </Switch>
        </Container>
      </div>
    );
  }
}

export default connect(mapStateToProps)(App);

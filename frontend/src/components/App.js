import React from 'react';
import ReactDOM from 'react-dom';
import { Switch, BrowserRouter as Router, Route } from 'react-router-dom';
import { connect } from 'react-redux';
import { Container } from 'reactstrap';

import AddEmployee from './AddEmployee';
import AddTask from './tasks/AddTask';
import ListEmployees from './ListEmployees';
import EmployeePage from './EmployeePage';
import HomePage from './HomePage';
import NotFound from './NotFound';
import NotAuthorized from './NotAuthorized';
import Header from './Header';

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
        <Header />
        <Container>
          <Switch>
            <Route exact path="/" component={HomePage} />
            <Route exact path="/add_employee" component={this.handleLoginState(AddEmployee)} />
            <Route exact path="/list_employees" component={this.handleLoginState(ListEmployees)} />
            <Route exact path="/employee/:id" component={this.handleLoginState(EmployeePage)} />
            <Route exact path="/add_tasks/:id" component={AddTask} />
            <Route component={NotFound} />
          </Switch>
        </Container>
      </div>
    );
  }
}

export default connect(mapStateToProps)(App);

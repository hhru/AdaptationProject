import React from 'react';
import { Switch, Route } from 'react-router-dom';
import { connect } from 'react-redux';
import { Container } from 'reactstrap';
import axios from 'axios';

import { setInitialized, setUser } from '../actions';
import AdminPage from './AdminPage';
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
  return {
    user: state.user,
    initialized: state.initialized,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    setInitialized: () => dispatch(setInitialized()),
    setUser: (user) => dispatch(setUser(user)),
  };
};

class LoginForm extends React.Component {
  componentDidMount() {
    this.refs.form.submit();
  }

  render() {
    return <form ref="form" method="POST" action="/api/login" />;
  }
}

class App extends React.Component {
  constructor(props) {
    super(props);

    this.handleAdminState = this.handleAdminState.bind(this);
  }

  componentDidMount() {
    axios
      .get('/api/user')
      .then((response) => {
        this.props.setUser(response.data);
        this.props.setInitialized();
      })
      .catch(() => {
        this.props.setInitialized();
      });
  }

  handleAdminState(component) {
    return this.props.user.isAdmin ? component : NotAuthorized;
  }

  render() {
    if (!this.props.initialized) {
      return null;
    }

    if (!this.props.user) {
      return <LoginForm />;
    }

    if (this.props.user.isStranger) {
      return null;
    }

    return (
      <div>
        <Switch>
          <Route exact path="/questionnaire/:id" />
          <Route component={Header} />
        </Switch>
        <Container>
          <Switch>
            <Route exact path="/" component={HomePage} />
            <Route exact path="/add_employee" component={AddEmployee} />
            <Route exact path="/admin_page" component={this.handleAdminState(AdminPage)} />
            <Route exact path="/edit_employee/:id" component={EditEmployee} />
            <Route exact path="/list_employees" component={ListEmployees} />
            <Route exact path="/employee/:id" component={EmployeePage} />
            <Route exact path="/employee/:id/questionnaire" component={QuestionnaireResult} />
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

export default connect(mapStateToProps, mapDispatchToProps)(App);

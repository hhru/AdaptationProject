import React from 'react';
import { Switch, Route } from 'react-router-dom';
import { Container } from 'reactstrap';

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
import Header from './Header';
import Donate from './Donate';
import Restrictor from './Restrictor';

class App extends React.Component {
  freeAccessRender = (Component) => {
    return (props) => (
      <Container>
        <Component {...props} />
      </Container>
    );
  };

  loginAccessRender = (Component) => {
    return (props) => (
      <Restrictor>
        <Header {...props} />
        <Container>
          <Component {...props} />
        </Container>
      </Restrictor>
    );
  };

  adminAccessRender = (Component) => {
    return (props) => (
      <Restrictor needAdminRole={true}>
        <Header {...props} />
        <Container>
          <Component {...props} />
        </Container>
      </Restrictor>
    );
  };

  render() {
    return (
      <Switch>
        <Route exact path="/add_tasks/:id" render={this.freeAccessRender(AddTask)} />
        <Route exact path="/questionnaire/:id" render={this.freeAccessRender(Questionnaire)} />

        <Route exact path="/admin_page" render={this.adminAccessRender(AdminPage)} />

        <Route exact path="/" render={this.loginAccessRender(HomePage)} />
        <Route exact path="/add_employee" render={this.loginAccessRender(AddEmployee)} />
        <Route exact path="/edit_employee/:id" render={this.loginAccessRender(EditEmployee)} />
        <Route exact path="/list_employees" render={this.loginAccessRender(ListEmployees)} />
        <Route exact path="/employee/:id" render={this.loginAccessRender(EmployeePage)} />
        <Route
          exact
          path="/employee/:id/questionnaire"
          render={this.loginAccessRender(QuestionnaireResult)}
        />
        <Route exact path="/add_tasks/:id" render={this.loginAccessRender(AddTask)} />
        <Route exact path="/questionnaire/:id" render={this.loginAccessRender(Questionnaire)} />
        <Route exact path="/donate" render={this.loginAccessRender(Donate)} />
        <Route render={this.loginAccessRender(NotFound)} />
      </Switch>
    );
  }
}

export default App;

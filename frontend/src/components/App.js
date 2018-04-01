import React from 'react';
import ReactDOM from 'react-dom';
import { Switch, BrowserRouter as Router, Route } from 'react-router-dom';

import AddEmployee from './AddEmployee';
import ListEmployees from './ListEmployees';
import EmployeePage from './EmployeePage';
import HomePage from './HomePage';
import NotFound from './NotFound';
import Header from './Header';

import { mainArticle } from './app.css';

const App = () => {
  return (
    <div>
      <Header />
      <Router>
        <div>
          <Switch>
            <Route exact path="/" component={HomePage} />
            <Route exact path="/add_employee" component={AddEmployee} />
            <Route exact path="/list_employees" component={ListEmployees} />
            <Route exact path="/employee_page" component={EmployeePage} />
            <Route component={NotFound} />
          </Switch>
        </div>
      </Router>
    </div>
  );
};

export default App;

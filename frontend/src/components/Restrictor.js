import React from 'react';
import { connect } from 'react-redux';
import axios from 'axios';

import { setInitialized, setUser } from '../actions';
import NotAuthorized from './NotAuthorized';

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

class Restrictor extends React.Component {
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

    if (this.props.needAdminRole && !this.props.user.isAdmin) {
      return <NotAuthorized />;
    }

    return this.props.children;
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Restrictor);

import React from 'react';
import ReactDOM from 'react-dom';
import { Alert } from 'reactstrap';
import { connect } from 'react-redux';

const mapStateToProps = (state) => {
  return { initialized: state.initialized };
};

class NotAuthorized extends React.Component {
  render() {
    if (this.props.initialized)
      return <Alert color="danger">Вы не авторизованы для просмотра этой страницы</Alert>;
    else return null;
  }
}

export default connect(mapStateToProps)(NotAuthorized);

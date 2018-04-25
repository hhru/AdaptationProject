import React from 'react';
import ReactDOM from 'react-dom';
import { Alert } from 'reactstrap';

class NotFound extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return <Alert color="danger">Страница не найдена</Alert>;
  }
}

export default NotFound;

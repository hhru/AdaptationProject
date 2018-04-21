import React from 'react';
import axios from 'axios';
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Form,
  FormGroup,
  Col,
  Row,
} from 'reactstrap';
import FunctionalTaskRows from './FunctionalTasks';

class EmployeeTasksModal extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tasksList: [],
    };
    this.toggle = this.toggle.bind(this);
    this.onOpened = this.onOpened.bind(this);
    this.download = this.download.bind(this);
  }

  toggle() {
    this.props.parentToggle();
  }

  componentDidMount() {}

  onOpened() {
    console.log('opened');
    const url = '/api/employee/tasks/' + this.props.employeeId;
    const self = this;
    axios
      .get(url)
      .then(function(response) {
        self.setState({
          tasksList: response.data.taskList.map((task) => {
            return {
              key: task.id,
              text: task.text,
              deadlineDate: task.deadlineDate,
              resources: task.resources,
            };
          }),
        });
      })
      .catch(function(error) {
        console.log(error);
        alert(error);
      });
  }

  download() {
    const url = '/api/employee/tasks/' + this.props.employeeId + '/doc';
    window.open(url);
  }

  render() {
    let self = this;
    let tasksToRender = this.state.tasksList.map((task) => (
      <TaskRow value={task} key={task.key} />
    ));
    console.log(tasksToRender);

    return (
      <Modal
        size={'lg'}
        onOpened={this.onOpened}
        isOpen={this.props.isOpen}
        toggle={this.toggle}
        className={this.props.className}
      >
        <ModalHeader toggle={this.toggle}>
          Задачи на испытательный срок
        </ModalHeader>
        <ModalBody>
          <Form>
            <FormGroup row>
              <Col sm={7}>
                <Row>
                  <Col sm={12}>
                    <b>Постановка задачи</b>
                  </Col>
                </Row>
              </Col>
              <Col sm={2}>
                <b>Срок выполнения</b>
              </Col>
              <Col sm={3}>
                <b>Ресурсы</b>
              </Col>
            </FormGroup>
            <hr />
            <FunctionalTaskRows />
            <hr />
            {tasksToRender}
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={this.download}>
            Скачать
          </Button>{' '}
          <Button color="secondary" onClick={this.toggle}>
            Закрыть
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

class TaskRow extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      text: props.value.text,
      deadlineDate: props.value.deadlineDate,
      resources: props.value.resources,
    };
  }

  render() {
    return (
      <FormGroup row>
        <Col sm={7}>
          <p type="textarea" name="text" id="taskText">
            {this.state.text}
          </p>
        </Col>
        <Col sm={2}>
          <p
            type="date"
            name="date"
            id="taskDate"
            placeholder="date placeholder"
          >
            {this.state.deadlineDate}
          </p>
        </Col>
        <Col sm={3}>
          <p type="textarea" name="text" id="taskResources">
            {this.state.resources}
          </p>
        </Col>
      </FormGroup>
    );
  }
}

export default EmployeeTasksModal;

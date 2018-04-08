import React from 'react';
import axios from 'axios';
import {
  Col,
  Button,
  Form,
  FormGroup,
  Input,
  Container,
  Row,
} from 'reactstrap';

class AddTask extends React.Component {
  constructor(props) {
    super(props);
    this.addTaskRow = this.addTaskRow.bind(this);
    this.submitTasks = this.submitTasks.bind(this);
    this.handleTaskEdit = this.handleTaskEdit.bind(this);
    this.handleRemoveTask = this.handleRemoveTask.bind(this);

    this.state = {
      keyCounter: -1,
      taskContent: [],
    };
  }

  componentDidMount() {
    const url = '/api/tasks/' + this.props.match.params.id;
    const self = this;
    axios
      .get(url)
      .then(function(response) {
        let newKey = self.state.keyCounter;
        self.setState({ keyCounter: -1 }, () =>
          self.setState(
            (previousState) => ({
              taskContent: response.data.taskList.map((task) => {
                newKey++;
                return {
                  id: task.id,
                  key: newKey,
                  number: newKey,
                  text: task.text,
                  deadlineDate: task.deadlineDate,
                  resources:
                    task.resources !== undefined
                      ? task.resources.split(',')
                      : null,
                  deleted: task.deleted,
                };
              }),
            }),
            () => self.setState({ keyCounter: newKey })
          )
        );
      })
      .catch(function(error) {
        console.log(error);
        alert(error);
      });
  }

  addTaskRow(event) {
    const key = this.state.keyCounter + 1;
    let taskRow = {
      number: key,
      key: key,
    };
    this.setState((prevState) => ({
      taskContent: [...prevState.taskContent, taskRow],
      keyCounter: prevState.keyCounter + 1,
    }));
  }

  submitTasks(event) {
    const url = '/api/tasks/submit';
    const self = this;
    axios
      .post(url, {
        taskList: this.state.taskContent.map((task) => {
          return {
            id: task.id,
            text: task.text,
            deadlineDate: task.deadlineDate,
            resources: task.resources.join(','),
            deleted: task.deleted,
          };
        }),
        key: this.props.match.params.id,
      })
      .then(function(response) {
        let newKey = -1;
        self.setState({ keyCounter: -1, taskContent: [] }, () =>
          self.setState(
            (previousState) => ({
              taskContent: response.data.taskList.map((task) => {
                newKey++;
                return {
                  id: task.id,
                  key: newKey,
                  number: newKey,
                  text: task.text,
                  deadlineDate: task.deadlineDate,
                  resources:
                    task.resources !== undefined
                      ? task.resources.split(',')
                      : null,
                  deleted: task.deleted,
                };
              }),
            }),
            () =>
              self.setState({ keyCounter: newKey }, () =>
                alert('Успешно сохранено!')
              )
          )
        );
      })
      .catch(function(error) {
        console.log(error);
        alert(error);
      });
  }

  handleTaskEdit(taskNumber, value) {
    const taskContent = this.state.taskContent;
    taskContent[taskNumber] = value;
    taskContent[taskNumber].key = taskNumber;
    this.setState({
      taskContent: taskContent,
    });
  }

  handleRemoveTask(taskId) {
    const taskContent = this.state.taskContent;
    taskContent[taskId].deleted = true;
    this.setState((prevState) => ({
      taskContent: taskContent,
    }));
  }

  render() {
    let self = this;
    let tasksToRender = this.state.taskContent
      .filter((task) => !task.deleted)
      .map((task) => (
        <TaskRow
          value={task}
          number={task.number}
          key={task.key}
          onChange={self.handleTaskEdit}
          onRemove={self.handleRemoveTask}
        />
      ));

    return (
      <Container>
        <Form>
          <FormGroup row>
            <Col sm={7}>
              <Row>
                <Col sm={12}>Постановка задачи</Col>
              </Row>
            </Col>
            <Col sm={2}>
              <h6>Срок выполнения</h6>
            </Col>
            <Col sm={3}>
              <Row>
                <Col sm={9}>
                  <h6>Ресурсы</h6>
                </Col>
              </Row>
            </Col>
          </FormGroup>
          <hr />
          {tasksToRender}
          <FormGroup row>
            <Col sm={{ size: 4, offset: 5 }}>
              <Button color="primary" size="lg" onClick={this.addTaskRow}>
                Еще задача
              </Button>
            </Col>
            <Col sm={2}>
              <Button
                color="success"
                size="lg"
                onClick={this.submitTasks}
                block
              >
                Отправить
              </Button>
            </Col>
          </FormGroup>
        </Form>
      </Container>
    );
  }
}

class TaskRow extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      id: props.value.id,
      number: props.value.number,
      text: props.value.text,
      deadlineDate: props.value.deadlineDate,
      resources: props.value.resources || [],
      deleted: props.value.deleted || false,
    };

    this.handleTextInput = this.handleTextInput.bind(this);
    this.handleDateInput = this.handleDateInput.bind(this);
    this.handleResourceInput = this.handleResourceInput.bind(this);
    this.handleRemoveTask = this.handleRemoveTask.bind(this);
  }

  render() {
    return (
      <FormGroup row>
        <Col sm={7}>
          <Input
            type="textarea"
            name="text"
            id="taskText"
            value={this.state.text}
            onChange={this.handleTextInput}
          />
        </Col>
        <Col sm={2}>
          <Input
            type="date"
            name="date"
            id="taskDate"
            placeholder="date placeholder"
            value={this.state.deadlineDate}
            onChange={this.handleDateInput}
          />
        </Col>
        <Col sm={3}>
          <Row>
            <Col md={9}>
              <Input
                type="select"
                name="selectMulti"
                id="taskResources"
                multiple
                value={this.state.resources}
                onChange={this.handleResourceInput}
              >
                <option value="intranet">Intranet</option>
                <option value="chief">Руководитель</option>
                <option value="chief-cu">Руководитель КУ</option>
                <option value="curator">Куратор</option>
                <option value="mentor">Ментор</option>
                <option value="wiki">wiki</option>
              </Input>
            </Col>
            <Col md={3}>
              <Button color="danger" onClick={this.handleRemoveTask}>
                x
              </Button>
            </Col>
          </Row>
        </Col>
      </FormGroup>
    );
  }

  handleRemoveTask(event) {
    this.setState(
      (prevState) => ({
        deleted: true,
      }),
      () => this.props.onRemove(this.props.number)
    );
  }

  handleTextInput(event) {
    let value = event.target.value;
    let self = this;
    this.setState(
      (prevState) => ({
        text: value,
      }),
      () => this.props.onChange(this.props.number, self.state)
    );
  }

  handleDateInput(event) {
    let value = event.target.value;
    let self = this;
    this.setState(
      (prevState) => ({
        deadlineDate: value,
      }),
      () => this.props.onChange(this.props.number, self.state)
    );
  }

  handleResourceInput(event) {
    let value = event.target.selectedOptions;
    let self = this;
    this.setState(
      (prevState) => ({
        resources: [...value].map((o) => o.value),
      }),
      () => this.props.onChange(this.props.number, self.state)
    );
  }
}

export default AddTask;

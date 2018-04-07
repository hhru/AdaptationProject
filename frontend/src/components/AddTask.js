import React from 'react';
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
    console.log(props);
    super(props);
    this.addTaskRow = this.addTaskRow.bind(this);
    this.submitTasks = this.submitTasks.bind(this);
    this.handleTaskEdit = this.handleTaskEdit.bind(this);
    this.handleRemoveTask = this.handleRemoveTask.bind(this);

    this.state = {
      taskList: [
        <TaskRow
          id={0}
          key={0}
          onChange={this.handleTaskEdit}
          onRemove={this.handleRemoveTask}
        />,
      ],
      taskContent: [],
    };
  }

  addTaskRow(event) {
    let taskRow = (
      <TaskRow
        id={this.state.taskList.length}
        key={this.state.taskList.length}
        onChange={this.handleTaskEdit}
        onRemove={this.handleRemoveTask}
      />
    );
    this.setState((prevState) => ({
      taskList: [...prevState.taskList, taskRow],
    }));
  }

  submitTasks(event) {
    console.log({
      tasks: this.state.taskContent,
      id: this.props.match.params.id,
    });
  }

  handleTaskEdit(taskId, value) {
    this.state.taskContent[taskId] = value;
  }

  handleRemoveTask(taskId) {
    this.setState((prevState) => ({
      taskList: prevState.taskList.filter((_, i) => i !== taskId),
      taskContent: prevState.taskContent.filter((_, i) => i !== taskId),
    }));
  }

  render() {
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
                  <h6>Постановка задачи</h6>
                </Col>
              </Row>
            </Col>
          </FormGroup>
          <hr />
          {this.state.taskList}
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
      text: undefined,
      deadlineDate: undefined,
      resources: undefined,
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
            id="exampleText"
            value={this.state.text}
            onChange={this.handleTextInput}
          />
        </Col>
        <Col sm={2}>
          <Input
            type="date"
            name="date"
            id="exampleDate"
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
                id="exampleSelectMulti"
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
    this.props.onRemove(this.props.id);
  }

  handleTextInput(event) {
    this.state.text = event.target.value;
    this.props.onChange(this.props.id, this.state);
  }

  handleDateInput(event) {
    this.state.deadlineDate = event.target.value;
    this.props.onChange(this.props.id, this.state);
  }

  handleResourceInput(event) {
    this.state.resources = [...event.target.selectedOptions].map(
      (o) => o.value
    );
    this.props.onChange(this.props.id, this.state);
  }
}

export default AddTask;

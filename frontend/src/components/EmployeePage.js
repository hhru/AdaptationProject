import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import FaCircle from 'react-icons/lib/fa/circle';
import FaAdjust from 'react-icons/lib/fa/adjust';
import FaExclamationCircle from 'react-icons/lib/fa/exclamation-circle';
import FaCheckCircle from 'react-icons/lib/fa/check-circle';

import { Jumbotron } from 'reactstrap';
import { Container, Row, Col } from 'reactstrap';
import { ListGroup, ListGroupItem } from 'reactstrap';
import { Form, FormGroup, Input } from 'reactstrap';
import { Popover, PopoverHeader, PopoverBody } from 'reactstrap';
import { Button } from 'reactstrap';
import '!style-loader!css-loader!./app.css';

class EmployeePage extends React.Component {
  constructor(props) {
    super(props);
    /*
    this.state = {
      employeeId: this.props.match.params.id,
      data: {
        id: 1,
        currentWorkflowStep: 'ADD',
        employee: {
          id: 1,
          firstName: 'Jason',
          lastName: 'Statham',
          middleName: 'Brutal',
          email: 'die@hard.com',
          inside: 'inside',
        },
        chief: {
          id: 1,
          firstName: 'Your',
          lastName: 'Boss',
          middleName: 'Topbanana',
          email: 'awdwada@www.rr',
          inside: 'inside2',
        },
        mentor: {
          id: 2,
          firstName: 'Some',
          lastName: 'Qqq',
          middleName: 'Chuvachek',
          email: 'awdwd@aad.tt',
          inside: 'inside3',
        },
        hr: {
          id: 3,
          firstName: 'Peter',
          lastName: 'Parker',
          middleName: 'Spider',
          email: 'spiderm@il.web',
          inside: 'inside4',
        },
        employmentDate: null,
        workflow: [
          {
            id: 1,
            type: 'ADD',
            status: 'DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 2,
            type: 'TASK_LIST',
            status: 'DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 3,
            type: 'WELCOME_MEETING',
            status: 'CURRENT',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 4,
            type: 'INTERIM_MEETING',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 5,
            type: 'INTERIM_MEETING_RESULT',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 6,
            type: 'FINAL_MEETING',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 7,
            type: 'FINAL_MEETING_RESULT',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
          {
            id: 8,
            type: 'QUESTIONNAIRE',
            status: 'NOT_DONE',
            deadlineDate: null,
            comment: null,
            overdue: true,
          },
        ],
        comments: [
          {
            id: 1,
            name: 'Jeka',
            text: 'Этот чувак дико тупил на встерче, спрашивал тупые вопросы и опоздал на полчаса и похоже ниче не понял че ему сказали и не оставил обратной связи',
            tag: 'tag1',
          },
          {
            id: 2,
            name: 'Jeka',
            text: 'Надо не забыть сделать ему пропуск',
            tag: 'tag2',
          },
          {
            id: 3,
            name: 'Leha',
            text: 'Это тот чувак с бородой с третьего этажа',
            tag: 'tag2',
          },
          {
            id: 4,
            name: 'Jeka',
            text: 'Оставляем на второй испытательный срок',
            tag: 'tag2',
          },
          {
            id: 5,
            name: 'Jeka',
            text: 'Ghjcnj tot jlby rjvvtyn xnj,s gjgjkybnm cgbcjr',
            tag: 'tag2',
          },
        ],
      },
    };*/

    this.state = {
      employeeId: this.props.match.params.id,
      data: {
        id: null,
        currentWorkflowStep: '',
        employee: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        chief: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        mentor: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        hr: {
          id: null,
          firstName: '',
          lastName: '',
          middleName: '',
          email: '',
          inside: '',
        },
        employmentDate: null,
        workflow: [
          {
            id: null,
            type: '',
            status: '',
            deadlineDate: null,
            comment: null,
            overdue: false,
          },
        ],
      },
    };
  }

  componentDidMount() {
    const url = '/api/employee/' + this.state.employeeId;
    const self = this;
    axios
      .get(url)
      .then(function(response) {
        console.log(response.data);
        self.setState({
          data: response.data,
        });
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  nextStep(self) {
    //self.state.data.workflow[0].overdue=!self.state.data.workflow[0].overdue;

    const url = '/api/employee/' + '1/' + 'step/next';
    axios
      .put(url)
      .then(function(response) {
        console.log('qqq');
        //self.forceUpdate();
      })
      .catch(function(error) {
        console.log(error);
      });
  }

  render() {
    const {
      firstName: employeeFirstName,
      middleName: employeeMiddleName,
      lastName: employeeLastName,
      email: employeeEmail,
    } = this.state.data.employee;
    const {
      firstName: chiefFirstName,
      middleName: chiefMiddleName,
      lastName: chiefLastName,
    } = this.state.data.chief;
    const {
      firstName: mentorFirstName,
      middleName: mentorMiddleName,
      lastName: mentorLastName,
    } = this.state.data.mentor;
    const {
      firstName: hrFirstName,
      middleName: hrMiddleName,
      lastName: hrLastName,
    } = this.state.data.hr;
    const employmentDate = this.state.data.employmentDate;
    const workflow = this.state.data.workflow;
    const comments = [
      {
        id: 1,
        name: 'Jeka',
        text:
          'Этот чувак дико тупил на встерче, спрашивал тупые вопросы и опоздал на полчаса и похоже ниче не понял че ему сказали и не оставил обратной связи',
        tag: 'tag1',
      },
      {
        id: 2,
        name: 'Jeka',
        text: 'Надо не забыть сделать ему пропуск',
        tag: 'tag2',
      },
      {
        id: 3,
        name: 'Leha',
        text: 'Это тот чувак с бородой с третьего этажа',
        tag: 'tag2',
      },
      {
        id: 4,
        name: 'Jeka',
        text: 'Оставляем на второй испытательный срок',
        tag: 'tag2',
      },
      {
        id: 5,
        name: 'Jeka',
        text: 'Ghjcnj tot jlby rjvvtyn xnj,s gjgjkybnm cgbcjr',
        tag: 'tag2',
      },
    ];

    return (
      <Container>
        <Jumbotron>
          <Row className="mb-3">
            <Col sm={{ size: 7, offset: 1 }}>
              <h3 className="mb-0 font-weight-bold">
                {`${employeeFirstName} ${employeeMiddleName} ${employeeLastName}`}
              </h3>
              <div className="mb-1 ml-2 text-info"> {employeeEmail} </div>
              <br />
              <div className="ml-4">
                <p className="mb-2 text-muted">
                  {`Начальник: ${chiefFirstName} ${chiefMiddleName} ${chiefLastName}`}
                </p>
                <p className="mb-2 text-muted">
                  {`Ментор: ${mentorFirstName} ${mentorMiddleName} ${mentorLastName}`}
                </p>
                <p className="text-muted">
                  {`HR: ${hrFirstName} ${hrMiddleName} ${hrLastName}`}
                </p>
              </div>
            </Col>
            <Col sm={{ size: 4 }} className="mt-5">
              <div className="p-2">
                <p className="font-italic">
                  {' '}
                  {`Дата выхода: ${employmentDate}`}{' '}
                </p>
                <p className="font-italic"> {`Остлаось: 5 дн.`} </p>
              </div>
            </Col>
          </Row>

          <Row>
            <Col sm={{ size: 5, offset: 1 }} className="mt-5">
              <Workflow data={workflow} />
              <Button
                outline
                color="secondary"
                className="mt-5"
                onClick={() => this.nextStep(this)}
              >
                Перевести далее
              </Button>
            </Col>
            <Col sm={{ size: 5 }}>
              <div className="ml-2">
                <h4>
                  <span className="text-muted">Комментарии</span>
                </h4>
              </div>
              <div>
                <Comments data={comments} />
                <Form>
                  <FormGroup>
                    <Input
                      rows="1"
                      type="text"
                      name="text"
                      placeholder="Написать комментарий"
                    />
                  </FormGroup>
                </Form>
              </div>
            </Col>
          </Row>
        </Jumbotron>
      </Container>
    );
  }
}

class Workflow extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div>
        {this.props.data.map((workflowStageData) => (
          <WorkflowStage
            deadlineDate={workflowStageData.deadlineDate}
            status={workflowStageData.status}
            overdue={workflowStageData.overdue}
            type={workflowStageData.type}
            key={workflowStageData.id}
            id={workflowStageData.id}
          />
        ))}
      </div>
    );
  }
}

class WorkflowStage extends React.Component {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.state = {
      popoverOpen: false,
    };
    this.selectIcon = this.selectIcon.bind(this);
  }

  selectIcon(status, overdue) {
    switch (overdue) {
      case true:
        return <FaExclamationCircle size={50} color="#DF6B62" />;
      default:
        switch (status) {
          case 'DONE':
            return <FaCheckCircle size={50} color="#70BD71" />;
          case 'CURRENT':
            return <FaAdjust size={50} color="#BDB370" />;
          default:
            return <FaCircle size={50} color="#C2C2C2" />;
        }
    }
  }

  typeTranslate(type) {
    switch (type) {
      case 'ADD':
        return 'Подготовка к сопровождению';
      case 'TASK_LIST':
        return 'Задачи на испытательный срок';
      case 'WELCOME_MEETING':
        return 'Велком встреча';
      case 'INTERIM_MEETING':
        return 'Промежуточная встреча';
      case 'INTERIM_MEETING_RESULT':
        return 'Результаты промежуточной встречи';
      case 'FINAL_MEETING':
        return 'Итоговая встреча';
      case 'FINAL_MEETING_RESULT':
        return 'Результаты итоговой встречи';
      case 'QUESTIONNAIRE':
        return 'Опросник';
    }
  }

  getDescription(type) {
    switch (type) {
      case 'ADD':
        return 'Ожидается день выхода на работу. Сейчас начальник готовит задачи на испытательный срок.';
      case 'TASK_LIST':
        return 'Необходимо получить задачи от руководителя, распечатать и подписать их.';
      case 'WELCOME_MEETING':
        return 'Необходимо забронировать переговорку и провести велком встречу.';
      case 'INTERIM_MEETING':
        return 'Необходимо забронировать переговорку и провести промежуточную встречу.';
      case 'INTERIM_MEETING_RESULT':
        return 'Необходимо заполнить результаты промежуточной встречи.';
      case 'FINAL_MEETING':
        return 'Необходимо забронировать переговорку и провести итоговую встречу.';
      case 'FINAL_MEETING_RESULT':
        return 'Необходимо заполнить результаты итоговой встречи.';
      case 'QUESTIONNAIRE':
        return 'Сотрудник получил опросник для новчика и работает над его заполнением.';
    }
  }

  toggle() {
    this.setState({
      popoverOpen: !this.state.popoverOpen,
    });
  }

  render() {
    const { deadlineDate, status, overdue, type } = this.props;

    return (
      <div
        id={'Popover-' + this.props.id}
        className="workflow-leonid"
        onClick={this.toggle}
      >
        {deadlineDate}
        {this.selectIcon(status, overdue)}
        <span className="ml-3">{this.typeTranslate(type)}</span>
        <br />
        <Popover
          placement="bottom"
          isOpen={this.state.popoverOpen}
          target={'Popover-' + this.props.id}
          toggle={this.toggle}
        >
          <PopoverHeader>{this.typeTranslate(type)}</PopoverHeader>
          <PopoverBody>{this.getDescription(type)}</PopoverBody>
        </Popover>
      </div>
    );
  }
}

class Comments extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <ListGroup className="mb-0 anyClass">
        {this.props.data.map((commentsData) => (
          <CommentsData
            tag={commentsData.tag}
            text={commentsData.text}
            name={commentsData.name}
            key={commentsData.id}
            id={commentsData.id}
          />
        ))}
      </ListGroup>
    );
  }
}

class CommentsData extends React.Component {
  constructor(props) {
    super(props);
  }

  nameWithDots(name) {
    return name + ':';
  }

  render() {
    const { tag, text, name } = this.props;

    return (
      <ListGroupItem className="d-flex justify-content-between lh-condensed">
        <div>
          <h6 className="my-0 mb-2">{this.nameWithDots(name)}</h6>
          <span>{text}</span>
        </div>
        <span className="text-muted">{tag}</span>
      </ListGroupItem>
    );
  }
}

export default EmployeePage;

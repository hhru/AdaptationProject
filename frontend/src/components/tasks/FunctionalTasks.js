import React from 'react';
import { FormGroup, Col } from 'reactstrap';

class FunctionalTaskRows extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <React.Fragment>
        <FormGroup row>
          <Col sm={6}>
            <p type="textarea" name="text" id="taskText">
              Получить целостное представление о группе компаний Headhunter: история, деятельность,
              структура, цели, продукты, корпоративная культура
            </p>
          </Col>
          <Col sm={3}>
            <p type="date" name="date" id="taskDate" placeholder="date placeholder">
              Середина испытательного срока
            </p>
          </Col>
          <Col sm={3}>
            <p type="textarea" name="text" id="taskResources">
              Intranet, Руководитель/Куратор*, Руководитель КУ
            </p>
          </Col>
        </FormGroup>
        <FormGroup row>
          <Col sm={6}>
            <p type="textarea" name="text" id="taskText">
              Подробное изучение работы подразделения: цели, задачи и роль подразделения в общей
              структуре, Внутренние процедуры и процессы, регламенты, правила взаимодействия со
              смежными подразделениями
            </p>
          </Col>
          <Col sm={3}>
            <p type="date" name="date" id="taskDate" placeholder="date placeholder">
              Первые 3 недели
            </p>
          </Col>
          <Col sm={3}>
            <p type="textarea" name="text" id="taskResources">
              Куратор*/Руководитель, wiki
            </p>
          </Col>
        </FormGroup>
      </React.Fragment>
    );
  }
}

export default FunctionalTaskRows;

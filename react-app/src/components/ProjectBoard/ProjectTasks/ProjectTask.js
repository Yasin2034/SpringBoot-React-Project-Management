import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { connect } from 'react-redux'
import { deleteProjectTask } from "../../../actions/backlogActions"
class ProjectTask extends Component {

    onDeleteClick(backlog_id, pt_id) {
        this.props.deleteProjectTask(backlog_id, pt_id);
    }

    render() {
        const { project_task } = this.props
        let priortyString
        let priortyClass
        if (project_task.priority === 1) {
            priortyClass = "bg-danger text-light"
            priortyString = "HIGH"
        }
        else if (project_task.priority === 2) {
            priortyClass = "bg-warning text-light"
            priortyString = "MEDIUM"
        }
        else if (project_task.priority === 3) {
            priortyClass = "bg-info text-light"
            priortyString = "LOW"
        }


        return (
            <div className="card mb-1 bg-light">
                <div className={`card-header text-primary ${priortyClass}`}>
                    ID: {project_task.projectSequence} -- Priority:{priortyString}
                </div>
                <div className="card-body bg-light">
                    <h5 className="card-title">{project_task.summary}</h5>
                    <p className="card-text text-truncate ">
                        {project_task.acceptanceCriteria}
                    </p>
                    <Link to={`/updateProjectTask/${project_task.projectIdentifier}/${project_task.projectSequence}`} className="btn btn-primary">
                        View / Update
                    </Link>

                    <button
                        className="btn btn-danger ml-4"
                        onClick={this.onDeleteClick.bind(this, project_task.projectIdentifier, project_task.projectSequence)}
                    >
                        Delete
                    </button>
                </div>
            </div>

        )
    }
}



export default connect(null, { deleteProjectTask })(ProjectTask);

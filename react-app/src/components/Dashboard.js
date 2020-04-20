import React, { Component } from 'react'
import ProjectItem from './Project/ProjectItem'
import CreateProjectButton from './Project/CreateProjectButton';
import { getProjects } from '../actions/projectActions';
import { connect } from 'react-redux';
import propTypes from 'prop-types'


class Dashboard extends Component {


    componentDidMount() {
        this.props.getProjects()
    }

    render() {
        const { projects } = this.props.project
        return (
            <div className="projects">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">
                            <h1 className="display-4 text-center">Projects</h1>
                            <br />
                            <CreateProjectButton />
                            <br />
                            <hr />

                            {projects.map(item => { return (<ProjectItem key={item.id} project={item} />) }

                            )}

                        </div>
                    </div>
                </div>
            </div>
        )
    }
}


Dashboard.propTypes = {
    project: propTypes.object.isRequired,
    getProjects: propTypes.func.isRequired
}

const mapStateToProps = state => ({
    project: state.project
})

export default connect(mapStateToProps, { getProjects })(Dashboard);
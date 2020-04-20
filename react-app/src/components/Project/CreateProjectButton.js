import React from 'react'
import { Link } from 'react-router-dom'

const CreateProjectButton = () => {
    return (
        <Link to="AddProject" className="btn btn-lg btn-info">
            Create a Project
        </Link>
    )
}


export default CreateProjectButton;
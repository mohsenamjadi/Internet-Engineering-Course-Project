import * as React from "react";
import CoursesSelected from "./CoursesSelected";
import CoursesSearchBar from "./CoursesSearchBar";
import CoursesPresented from "./CoursesPresented";
import Header from "../general/Header";
import Footer from "../general/Footer";
import CoursesService from "../../services/CoursesService";
import {toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {OK, TOAST_MESSAGE_OK} from "../../config/config";
import PlanService from "../../services/PlanService";
import AuthService from "../../services/AuthService";

export default class Courses extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            presentedCourses: null,
            selectedCourses: null,
        };

        this.handleLogout = this.handleLogout.bind(this);
        this.handleFilter = this.handleFilter.bind(this);
        this.handleSearch = this.handleSearch.bind(this);
        this.handleAdd = this.handleAdd.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.submitSelectedCourses = this.submitSelectedCourses.bind(this);
        this.resetSelectedCourses = this.resetSelectedCourses.bind(this);
    }

    async submitSelectedCourses() {
        let response = await CoursesService.courseSubmitSelected();
        let courses = await CoursesService.getSelectedCourses();
        console.log(courses);
        if (response === OK) {
            toast.success(TOAST_MESSAGE_OK, {
                position: "bottom-center"
            });
        } else {
            toast.error(response.toString(), {
                position: "bottom-center",
            });
        }
        this.setState({selectedCourses : courses});
    }

    async resetSelectedCourses() {
        let response = await CoursesService.courseResetSelected();
        let courses = await CoursesService.getSelectedCourses();
        console.log(courses);
        if (response === OK) {
            toast.success(TOAST_MESSAGE_OK, {
                position: "bottom-center"
            });
        } else {
            toast.error(response.toString(), {
                position: "bottom-center",
            });
        }
        this.setState({selectedCourses : courses});
    }

    handleLogout = async () => {
        await AuthService.logout();
    }

    async handleAdd(code, classCode) {
        let response = await CoursesService.addCourseToSelected(code , classCode);
        let courses = await CoursesService.getSelectedCourses();
        console.log(courses);
        if (response === OK) {
            toast.success(TOAST_MESSAGE_OK, {
                position: "bottom-center"
            });
        } else {
            toast.error(response.toString(), {
                position: "bottom-center",
            });
        }
        this.setState({selectedCourses : courses});
    };

    async handleDelete(code, classCode) {
        let response = await CoursesService.removeFromSelectedCourses(code , classCode);
        let courses = await CoursesService.getSelectedCourses();
        if (response === OK) {
            toast.success(TOAST_MESSAGE_OK, {
                position: "bottom-center"
            });
        } else {
            toast.error(response.toString(), {
                position: "bottom-center",
            });
        }
        this.setState({selectedCourses : courses});
    };

    componentDidMount() {
        this.getSelectedCourses();
        this.getAllPresentedCourses();
        toast.configure({rtl: true, className: "text-center"});
    }

    async getSelectedCourses() {
        let selectedCourses = await CoursesService.getSelectedCourses();
        console.log(selectedCourses);
        this.setState(
            {
                selectedCourses: selectedCourses
            }
        )
    }

    async handleFilter(filter) {
        let presentedCourses = await CoursesService.getPresentedCoursesByType(filter);
        console.log('presentedCourses');
        this.setState(
            {
                presentedCourses: presentedCourses
            }
        );
    }

    async handleSearch(filter) {
        await CoursesService.setSearchFilter(filter);
        let presentedCourses = await CoursesService.getPresentedCoursesByName();
        console.log('presentedCourses');
        this.setState(
            {
                presentedCourses: presentedCourses
            }
        );
    }

    async getAllPresentedCourses() {
        let presentedCourses = await CoursesService.getCourses();
        console.log(presentedCourses);
        this.setState(
            {
                presentedCourses: presentedCourses
            }
        );
    }

    renderMain() {
        return (
            <div className="h-100">
                <Header/>
                <CoursesSelected
                    selectedCourses={this.state.selectedCourses}
                    onDelete={this.handleDelete}
                    onSubmit={this.submitSelectedCourses}
                    onReset={this.resetSelectedCourses}
                />

                <CoursesSearchBar
                    onSearch={this.handleSearch}
                />

                <CoursesPresented
                    presentedCourses={this.state.presentedCourses}
                    onAdd={this.handleAdd}
                    onFilter={this.handleFilter}
                    selectedCourses={this.state.selectedCourses}
                />

                <div className="md-modal" id="modal">
                    <div className="md-content">
                        <div className="row no-gutters" style={{marginBottom: '30px'}}>
                            <span style={{fontSize: 'larger', fontWeight: 'bold'}}>
                                آیا می خواهید از حساب کاربری خود خارج شوید؟
                            </span>
                        </div>

                        <div className="row no-gutters">
                            <div className="col-2 mr-auto m-2">
                                <a href='#' style={{textDecoration: 'none'}}>
                                    <button className="btn-kenseraf">انصراف</button>
                                </a>
                            </div>
                            <div className="col-2 m-2">
                                <a href='/login' style={{textDecoration: 'none'}}>
                                    <button onClick={this.handleLogout} className="btn-khorooj">خروج</button>
                                </a>
                            </div>
                        </div>

                    </div>
                </div>
                <div className="md-overlay"></div>

                <Footer/>
            </div>
        );
    }

    renderLoading() {
        return (
            <div className="h-100 m-5 center-text p-5">
                <div className="spinner-grow text-danger m-5" role="status">
                    <span className="sr-only">Loading...</span>
                </div>
            </div>
        );
    }

    render() {
        return (
            <div className="h-100">
                {this.state.presentedCourses !== null ?
                    this.renderMain()
                    :
                    this.renderLoading()
                }
            </div>
        );
    }
}
import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/classroom',
      name: 'all-classrooms',
      component: () => import('../views/classroom/ClassroomListView.vue'),
    },
    {
      path: '/classroom/add',
      name: 'add-classroom',
      component: () => import('../views/classroom/ClassroomCrud.vue')
    },
    {
      path: '/classroom/:id',
      name: 'edit-classroom',
      component: () => import('../views/classroom/ClassroomCrud.vue')
    },
    {
      path: '/subject',
      name: 'all-subjects',
      component: () => import('../views/subject/SubjectListView.vue')
    },
    {
      path: '/subject/add',
      name: 'add-subject',
      component: () => import('../views/subject/SubjectCrud.vue')
    },
    {
      path: '/subject/:id',
      name: 'edit-subject',
      component: () => import('../views/subject/SubjectCrud.vue')
    },
    {
      path: '/professor',
      name: 'all-professors',
      component: () => import('../views/professor/ProfessorListView.vue')
    },
    {
      path: '/professor/add',
      name: 'add-professor',
      component: () => import('../views/professor/ProfessorCrud.vue')
    },
    {
      path: '/professor/:id',
      name: 'edit-professor',
      component: () => import('../views/professor/ProfessorCrud.vue')
    },
    {
      path: '/academic-cycle',
      name: 'all-academic-cycles',
      component: () => import('../views/academic-cycle/AcademicCycleListView.vue')
    },
    {
      path: '/academic-cycle/add',
      name: 'add-academic-cycle',
      component: () => import('../views/academic-cycle/AcademicCycleCrud.vue')
    },
    {
      path: '/academic-cycle/:id',
      name: 'edit-academic-cycle',
      component: () => import('../views/academic-cycle/AcademicCycleCrud.vue')
    },
    {
      path: '/academic-cycle/:academicCycleId/model',
      name: 'all-ac-cy-models',
      component: () => import('../views/academic-cycle/ac-cy-schedule-model/AcCyScheduleModelListView.vue')
    },
    {
      path: '/academic-cycle/:academicCycleId/model/add',
      name: 'ac-cy-model-add',
      component: () => import('../views/academic-cycle/ac-cy-schedule-model/AcCyScheduleModelCrud.vue')
    },
    {
      path: '/academic-cycle/:academicCycleId/model/:acCyScheduleModelId',
      name: 'all-ac-cy-schedules',
      component: () => import('../views/academic-cycle/ac-cy-schedule-model/ac-cy-schedule/AcCyScheduleListView.vue')
    }
  ]
})

export default router

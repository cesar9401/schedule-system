<script setup lang="ts">

import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import { Professor, ProfessorSubject, Subject } from '@/model/schedule.model';
import router from '@/router';
import ProfessorService from '@/services/ProfessorService';
import SubjectService from '@/services/SubjectService';
import { onMounted, reactive, UnwrapNestedRefs } from 'vue';
import VueMultiselect from 'vue-multiselect';

const professor = reactive({ data: new Professor() });
const subjects: UnwrapNestedRefs<{ data: Subject[] }> = reactive({ data: [] });

async function create() {
  try {
    const response = await ProfessorService.save(professor.data);
    window.alert('Cambios guardados');
    await router.push({ name: 'all-professors' });
  } catch (e) {
    console.error(e);
    window.alert('Something went  wrong!');
  }
}

async function getSubjectsAndProfessorById() {
  try {
    // find subjects
    const subjResponse = await SubjectService.findAll();
    subjects.data = subjResponse.data;

    const id = router.currentRoute.value.params['id'];
    if (!id) return;

    // find professor
    const response = await ProfessorService.findById(Number(id));
    professor.data = response.data;
  } catch (e) {
    console.error(e);
    window.alert('something went wrong!');
    await router.push({ name: 'all-professors' })
  }
}

function addProfessorSubject() {
  professor.data.professorSubjects.push(new ProfessorSubject());
}

function removeProfessorSubject(index: number) {
  professor.data.professorSubjects.splice(index, 1);
}

onMounted(() => {
  getSubjectsAndProfessorById();
});

</script>
<template>
  <div class="container">
    <Header to="/professor" title="Regresar a docentes" :type-header="HeaderEnum.HEADER_CRUD"></Header>

    <div class="my-5">
      <h1 class="text-success text-center">Agregar docente</h1>
    </div>
    <form @submit.prevent="create">
      <div class="row">
        <div class="mb-3 col-lg-6 col-12">
          <label for="name">Nombre</label>
          <input v-model="professor.data.fullName" id="name" class="form-control" placeholder="Eje: Luis Miguel">
        </div>

        <div class="mb-3 col-lg-6 col-12">
          <label for="email">Email</label>
          <input v-model="professor.data.email" id="email" class="form-control" placeholder="Eje: luismi@modela2.com">
        </div>

        <div class="mb-3 col-lg-6 col-12">
          <label for="qualification">Calificaci&oacute;n promedio</label>
          <input v-model="professor.data.averageQualification" id="qualification" class="form-control" placeholder="Eje: 75.5">
        </div>

        <div id="professor-subject-area" class="row border-top border-bottom p-4 mt-2">
          <div class="my-3 col-12">
            <h1 class="text-center h4">Cursos</h1>
          </div>

          <div class="d-flex align-items-end justify-content-between gap-2" v-for="(profSubj, index) in professor.data.professorSubjects" :key="index">
            <div class="mb-3 col-4">
              <label>Curso</label>
              <VueMultiselect v-model="profSubj.subject" :options="subjects.data" track-by="subjectId" label="name" class="w-100"></VueMultiselect>
            </div>
            <div class="mb-3 flex-fill">
              <label>Calificaci&oacute;n</label>
              <input v-model="profSubj.qualification" class="form-control" placeholder="Eje: 75.5">
            </div>
            <div class="mb-3 flex-fill">
              <label>A&ntilde;os de experiencia</label>
              <input v-model="profSubj.yearsOfExperience" class="form-control" placeholder="Eje: 3">
            </div>
            <div class="mb-3 mt-auto">
              <button @click="removeProfessorSubject(index)" type="button" class="btn btn-outline-danger d-inline-flex btn-sm">
                <span class="material-symbols-outlined">delete</span>
              </button>
            </div>
          </div>
          <div class="text-end">
            <button @click="addProfessorSubject()" type="button" class="btn btn-outline-success d-inline-flex justify-content-center align-items-center gap-2">
              <span class="material-symbols-outlined">add</span>
              Nuevo curso
            </button>
          </div>
        </div>
      </div>
      <div class="d-flex justify-content-end my-4">
        <button type="submit" class="btn btn-success col-2">Agregar</button>
      </div>
    </form>
  </div>
</template>

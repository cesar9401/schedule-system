<script setup lang="ts">

import Header from '@/components/Header.vue';
import { CategoryEnum } from '@/model/CategoryEnum';
import { HeaderEnum } from '@/model/HeaderEnum';
import { Category, Professor, ProfessorContractDay, ProfessorSubject, Subject } from '@/model/schedule.model';
import router from '@/router';
import CategoryService from '@/services/CategoryService';
import ProfessorService from '@/services/ProfessorService';
import SubjectService from '@/services/SubjectService';
import { onMounted, reactive, UnwrapNestedRefs } from 'vue';
import VueMultiselect from 'vue-multiselect';

const professor = reactive({ data: new Professor() });
const subjects: UnwrapNestedRefs<{ data: Subject[] }> = reactive({ data: [] });
const days: UnwrapNestedRefs<{ data: Category[] }> = reactive({ data: [] })

async function create() {
  try {
    // process contract days here
    professor.data.contractDays.forEach(setStartAndEndTimeForBack);

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
    subjects.data = [...subjResponse.data];

    const catResponse = await CategoryService.findByParentInternalId(CategoryEnum.DAY_OF_WEEK);
    days.data = [...catResponse.data];

    const id = router.currentRoute.value.params['id'];
    if (!id) return;

    // find professor
    const response = await ProfessorService.findById(Number(id));
    if (response.data.contractDays) {
      response.data.contractDays.forEach(setStartAndEndTimeForView);
    }

    professor.data = response.data;
  } catch (e) {
    console.error(e);
    window.alert('something went wrong!');
    await router.push({ name: 'all-professors' })
  }
}

function setStartAndEndTimeForBack(contract: ProfessorContractDay) {
  const startH = contract.start.hours < 10 ? `0${contract.start.hours}` : `${contract.start.hours}`;
  const startM = contract.start.minutes < 10 ? `0${contract.start.minutes}` : `${contract.start.minutes}`;

  const endH = contract.end.hours < 10 ? `0${contract.end.hours}` : `${contract.end.hours}`;
  const endM = contract.end.minutes < 10 ? `0${contract.end.minutes}` : `${contract.end.minutes}`;

  contract.startTime = `1900-01-01 ${startH}:${startM}:00`;
  contract.endTime = `1900-01-01 ${endH}:${endM}:00`;
}

function setStartAndEndTimeForView(contract: ProfessorContractDay) {
  const start = new Date(contract.startTime);
  const end = new Date(contract.endTime);

  contract.start = { hours: start.getHours(), minutes: start.getMinutes() };
  contract.end = { hours: end.getHours(), minutes: end.getMinutes() };
}

function addProfessorSubject() {
  professor.data.professorSubjects.push(new ProfessorSubject());
}

function removeProfessorSubject(index: number) {
  professor.data.professorSubjects.splice(index, 1);
}

function addProfessorContractDay() {
  professor.data.contractDays.push(new ProfessorContractDay());
}

function removeProfessorContractDay(index: number) {
  professor.data.contractDays.splice(index, 1);
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
          <input v-model="professor.data.averageQualification" id="qualification" class="form-control"
                 placeholder="Eje: 75.5">
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
            <div class="mb-3">
              <button @click="removeProfessorSubject(index)" type="button" class="btn btn-outline-danger d-inline-flex btn-sm">
                <span class="material-symbols-outlined">delete</span>
              </button>
            </div>
          </div>
          <div class="text-end">
            <button @click="addProfessorSubject()" type="button" class="btn btn-outline-success d-inline-flex justify-content-center align-items-center gap-2">
              <span class="material-symbols-outlined">add</span>
              Agregar
            </button>
          </div>
        </div>

        <div id="professor-contract-area" class="row border-top border-bottom p-4 mt-2">
          <div class="my-3 col-12">
            <h1 class="text-center h4">Horas de contrato</h1>
          </div>

          <div class="d-flex align-items-end justify-content-between gap-2" v-for="(contractDay, index) in professor.data.contractDays" :key="index">
            <div class="mb-3 col-4">
              <label>D&iacute;a</label>
              <VueMultiselect v-model="contractDay.catDay" :options="days.data" track-by="categoryId" label="description" class="w-100"></VueMultiselect>
            </div>

            <div class="mb-3 flex-fill">
              <label>Hora de entrada</label>
              <VueDatePicker v-model="contractDay.start" time-picker/>
            </div>

            <div class="mb-3 flex-fill">
              <label>Hora de salida</label>
              <VueDatePicker v-model="contractDay.end" time-picker/>
            </div>

            <div class="mb-3 mt-auto">
              <button @click="removeProfessorContractDay(index)" type="button" class="btn btn-outline-danger d-inline-flex btn-sm">
                <span class="material-symbols-outlined">delete</span>
              </button>
            </div>
          </div>
          <div class="text-end">
            <button @click="addProfessorContractDay()" type="button" class="btn btn-outline-success d-inline-flex justify-content-center align-items-center gap-2">
              <span class="material-symbols-outlined">add</span>
              Agregar
            </button>
          </div>
        </div>
      </div>
      <div class="d-flex justify-content-end my-4">
        <button type="submit" class="btn btn-success col-2">Guardar</button>
      </div>
    </form>
  </div>
</template>

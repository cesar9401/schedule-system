<script setup lang="ts">
import Header from '@/components/Header.vue';
import { CategoryEnum } from '@/model/CategoryEnum';
import { HeaderEnum } from '@/model/HeaderEnum';
import { AcademicCycle, AcCyClassDay, AcCySubject, Category, Subject } from '@/model/schedule.model';
import router from '@/router';
import AcademicCycleService from '@/services/AcademicCycleService';
import CategoryService from '@/services/CategoryService';
import SubjectService from '@/services/SubjectService';
import { onMounted, reactive, UnwrapNestedRefs } from 'vue';
import VueMultiselect from 'vue-multiselect';

const academicCycle = reactive({ data: new AcademicCycle() });
const subjects: UnwrapNestedRefs<{ data: Subject[] }> = reactive({ data: [] });
const days: UnwrapNestedRefs<{ data: Category[] }> = reactive({ data: [] });

async function create() {
  try {
    academicCycle.data.classDays.forEach(setStartAndEndTimeForBack);
    const response = await AcademicCycleService.save(academicCycle.data);
    window.alert('Cambios guardados');
    await router.push({ name: 'all-academic-cycles' });
  } catch (e) {
    console.error(e);
    window.alert('Something went wrong');
  }
}

function addClassDay() {
  academicCycle.data.classDays.push(new AcCyClassDay());
}

function removeClassDay(index: number) {
  academicCycle.data.classDays.splice(index, 1);
}

function addAcCySubject() {
  academicCycle.data.acCySubjects.push(new AcCySubject());
}

function removeAcCySubject(index: number) {
  academicCycle.data.acCySubjects.splice(index, 1);
}

function setStartAndEndTimeForView(classDay: AcCyClassDay) {
  const start = new Date(classDay.startTime);
  const end = new Date(classDay.endTime);

  classDay.start = { hours: start.getHours(), minutes: start.getMinutes() };
  classDay.end = { hours: end.getHours(), minutes: end.getMinutes() };
}

function setStartAndEndTimeForBack(classDay: AcCyClassDay) {
  const startH = classDay.start.hours < 10 ? `0${classDay.start.hours}` : `${classDay.start.hours}`;
  const startM = classDay.start.minutes < 10 ? `0${classDay.start.minutes}` : `${classDay.start.minutes}`;

  const endH = classDay.end.hours < 10 ? `0${classDay.end.hours}` : `${classDay.end.hours}`;
  const endM = classDay.end.minutes < 10 ? `0${classDay.end.minutes}` : `${classDay.end.minutes}`;

  classDay.startTime = `1900-01-01 ${startH}:${startM}:00`;
  classDay.endTime = `1900-01-01 ${endH}:${endM}:00`;
}

async function getData() {
  try {
    const catResponse = await CategoryService.findByParentInternalId(CategoryEnum.DAY_OF_WEEK);
    days.data = [...catResponse.data];

    const subjRepsonse = await SubjectService.findAll();
    subjects.data = [...subjRepsonse.data];

    const id = router.currentRoute.value.params['id'];
    if (!id) return;

    // TODO: find by id
    const response = await AcademicCycleService.findById(Number(id));
    if (response.data.classDays) {
      response.data.classDays.forEach(setStartAndEndTimeForView);
    }

    academicCycle.data = response.data;
  } catch (e) {
    console.error(e);
    window.alert('Something went wrong!');
    await router.push({ name: 'all-academic-cycles' });
  }
}

onMounted(() => {
  getData();
});

</script>
<template>
  <div class="container">
    <Header to="/academic-cycle" title="Regresar a ciclos academicos" :type-header="HeaderEnum.HEADER_CRUD"/>

    <div class="my-5">
      <h1 class="text-success text-center">Agregar ciclo academico</h1>
    </div>
    <form @submit.prevent="create">
      <div class="row">
        <div class="mb-3 col-lg-6 col-12">
          <label for="name">Nombre</label>
          <input v-model="academicCycle.data.name" id="name" class="form-control" placeholder="Eje: Ciclo academico 2023">
        </div>
        <div class="mb-3 col-lg-6 col-12">
          <label for="description">Descripci&oacute;n</label>
          <input v-model="academicCycle.data.description" id="description" class="form-control" placeholder="Eje: Descripción breve">
        </div>

        <div id="academic-cycle-class-day-area" class="row border-top border-bottom p-4 mt-2">
          <div class="my-3 col-12">
            <h1 class="text-center h4">Secciones para el ciclo academico</h1>
          </div>
          <div class="d-flex align-items-end justify-content-between gap-2" v-for="(ayCySubj, index) in academicCycle.data.acCySubjects">
            <div class="mb-3 col-4">
              <label>Curso</label>
              <VueMultiselect v-model="ayCySubj.subject" :options="subjects.data" track-by="subjectId" label="name" class="w-100"></VueMultiselect>
            </div>
            <div class="mb-3 flex-fill">
              <label>Sección</label>
              <input v-model="ayCySubj.sectionCode" class="form-control" placeholder="Eje: A-1">
            </div>
            <div class="mb-3 flex-fill">
              <label>N&uacute;mero de periodos</label>
              <input v-model="ayCySubj.numberOfPeriods" class="form-control" placeholder="Eje: 2">
            </div>
            <div class="mb-3 flex-fill">
              <label>Estudiantes asignados</label>
              <input v-model="ayCySubj.assignedStudents" class="form-control" placeholder="Eje: 25">
            </div>
            <div class="mb-3 mt-auto">
              <button @click="removeAcCySubject(index)" type="button" class="btn btn-outline-danger d-inline-flex btn-sm">
                <span class="material-symbols-outlined">delete</span>
              </button>
            </div>
          </div>
          <div class="text-end">
            <button @click="addAcCySubject()" type="button" class="btn btn-outline-success d-inline-flex justify-content-center align-items-center gap-2">
              <span class="material-symbols-outlined">add</span>
              Agregar
            </button>
          </div>
        </div>

        <div id="academic-cycle-class-day-area" class="row border-top border-bottom p-4 mt-2">
          <div class="my-3 col-12">
            <h1 class="text-center h4">D&iacute;as de clases</h1>
          </div>
          <div class="d-flex align-items-end justify-content-between gap-2" v-for="(classDay, index) in academicCycle.data.classDays">
            <div class="mb-3 col-4">
              <label>D&iacute;a</label>
              <VueMultiselect v-model="classDay.catDay" :options="days.data" track-by="categoryId" label="description" class="w-100"></VueMultiselect>
            </div>

            <div class="mb-3 flex-fill">
              <label>Hora de entrada</label>
              <VueDatePicker v-model="classDay.start" time-picker/>
            </div>

            <div class="mb-3 flex-fill">
              <label>Hora de salida</label>
              <VueDatePicker v-model="classDay.end" time-picker/>
            </div>

            <div class="mb-3 mt-auto">
              <button @click="removeClassDay(index)" type="button" class="btn btn-outline-danger d-inline-flex btn-sm">
                <span class="material-symbols-outlined">delete</span>
              </button>
            </div>
          </div>
          <div class="text-end">
            <button @click="addClassDay()" type="button" class="btn btn-outline-success d-inline-flex justify-content-center align-items-center gap-2">
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

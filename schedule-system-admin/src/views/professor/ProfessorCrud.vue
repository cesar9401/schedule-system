<script setup lang="ts">

import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import { Professor } from '@/model/schedule.model';
import router from '@/router';
import ProfessorService from '@/services/ProfessorService';
import { onMounted, reactive } from 'vue';

const professor = reactive({ data: new Professor() });

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

async function findById(id: number) {
  try {
    const response = await ProfessorService.findById(id);
    professor.data = response.data;
  } catch (e) {
    console.error(e);
    window.alert('something went wrong!');
    await router.push({ name: 'all-professors' })
  }
}

onMounted(() => {
  const id = router.currentRoute.value.params['id'];
  if (!id) return;
  findById(Number(id));
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

      </div>
      <div class="d-flex justify-content-end my-4">
        <button type="submit" class="btn btn-success col-1">Agregar</button>
      </div>
    </form>

  </div>
</template>

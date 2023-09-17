<script setup lang="ts">
import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import { Classroom } from '@/model/schedule';
import router from '@/router';
import ClassroomService from '@/services/ClassroomService';
import { onMounted, reactive } from 'vue';

const classroom = reactive({ data: new Classroom() });

async function create() {
  try {
    const response = await ClassroomService.save(classroom.data)
    window.alert('Cambios guardados');
    await router.push({ name: 'all-classrooms' });
  } catch (e) {
    console.error(e);
    window.alert("Something went wrong!");
  }
}

async function findById(id: number) {
  try {
    const response = await ClassroomService.findById(id);
    const classroom1: Classroom = response.data;
    classroom.data = classroom1;

  } catch (e) {
    window.alert('Algo salió mal');
    await router.push({ name: 'all-classrooms' });
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
    <Header to='/classroom' title='Regresar a salones' :typeHeader="HeaderEnum.HEADER_CRUD"></Header>

    <div class="my-5">
      <h1 class="text-success text-center">Agregar sal&oacute;n</h1>
    </div>
    <form @submit.prevent="create">
      <div class="row">
        <div class="mb-3 col-lg-6 col-12">
          <label for="name">Nombre del sal&oacute;n</label>
          <input v-model="classroom.data.name" id="name" class="form-control" placeholder="Eje: A-101">
        </div>
        <div class="mb-3 col-lg-6 col-12">
          <label for="capacity">Capacidad</label>
          <input v-model="classroom.data.recommendedCapacity" id="capacity" class="form-control" placeholder="Eje: 100">
        </div>
      </div>
      <div class="d-flex justify-content-end my-4">
        <button type="submit" class="btn btn-success col-1">Agregar</button>
      </div>
    </form>
  </div>
</template>

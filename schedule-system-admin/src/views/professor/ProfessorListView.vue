<script setup lang="ts">

import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import type { Professor } from '@/model/schedule.model';
import ProfessorService from '@/services/ProfessorService';
import { onMounted, reactive, UnwrapNestedRefs } from 'vue';

const professors: UnwrapNestedRefs<{ data: Professor[] }> = reactive({ data: [] })

async function findAll() {
  try {
    const professor1 = await ProfessorService.findAll();
    professors.data = professor1.data;
  } catch (e) {
    console.error(e);
    window.alert('Something went wrong');
  }
}

onMounted(() => {
  findAll();
});

</script>
<template>
  <div class="container">
    <Header to="/professor/add" :type-header="HeaderEnum.HEADER_LIST_VIEW" />
    <div class="my-5">
      <h1 class="text-success text-center">Docentes</h1>
    </div>
    <div>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Nombre</th>
            <th scope="col">Email</th>
            <th scope="col">Fecha de contrataci&oacute;n</th>
            <th scope="col">Calificación</th>
            <th class="th-action" scope="col">Acciones</th>
          </tr>
        </thead>
        <tbody class="table-group-divider">
          <tr v-for="professor of professors.data">
            <td>{{ professor.fullName }}</td>
            <td>{{ professor.email }}</td>
            <td>{{ professor.dateOfHire }}</td>
            <td>{{ professor.averageQualification }}</td>
            <td>
              <div class="d-inline-flex justify-content-end align-items-center">
                <router-link :to="'/professor/' + professor.professorId" class="btn btn-sm btn-outline-success">
                  <span class="material-symbols-outlined">edit</span>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

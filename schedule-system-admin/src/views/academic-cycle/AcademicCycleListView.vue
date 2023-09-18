<script setup lang="ts">
import Header from '@/components/Header.vue';
import { HeaderEnum } from '@/model/HeaderEnum';
import type { AcademicCycle } from '@/model/schedule.model';
import AcademicCycleService from '@/services/AcademicCycleService';
import { onMounted, reactive, UnwrapNestedRefs } from 'vue';

const academicCycles: UnwrapNestedRefs<{ data: AcademicCycle[] }> = reactive({ data: [] });

async function findAll() {
  try {
    const response = await AcademicCycleService.findAll();
    academicCycles.data = response.data;
  } catch (e) {
    console.error(e);
    window.alert('Something went wrong!');
  }
}

onMounted(() => {
  findAll();
});
</script>
<template>
  <div class="container">
    <Header to="/academic-cycle/add" :type-header="HeaderEnum.HEADER_LIST_VIEW"/>
    <div class="my-5">
      <h1 class="text-success text-center">Ciclos academicos</h1>
    </div>
    <div>
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Nombre</th>
            <th scope="col">Descripci&oacute;n</th>
            <th scope="col" class="th-action">Acciones</th>
          </tr>
        </thead>
        <tbody class="table-group-divider">
          <tr v-for="ac of academicCycles.data">
            <td>{{ ac.name }}</td>
            <td>{{ ac.description }}</td>
            <td>
              <div class="d-inline-flex justify-content-end align-items-center">
                <router-link :to="'/academic-cycle/' + ac.academicCycleId" class="btn btn-sm btn-outline-success">
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

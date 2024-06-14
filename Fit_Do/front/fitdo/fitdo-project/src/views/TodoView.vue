<template>
    <div class="app-container">
        <div class="form-group">
            <label for="birthday"></label>
            <input type="date" id="birthday" max="2024-07-30" min="2020-05-05" v-model="data.date" @input="check">
        </div>
        <hr>

        <div class="goalwrite-box" v-if="dateString <= data.date">
            <div v-if="store.goaldata && store.goaldata.length > 0" class="goal-container">
                <div @click="toggleEditGoal" class="editGoalform">
                    <div v-if="editingGoal" class="editing-goal-container">
                        <input v-model="editedGoalContent" type="text" @click.stop class="goal-input"/>
                        <button @click="editGoal" class="edit-button">Edit</button>
                    </div>
                    <div v-else>
                        <h2 style="text-align: center;"><button class="goal-button">{{ store.goaldata[0].goalContent }}</button></h2><hr>
                    </div>
                </div>
                
                <h3 style="text-align: start; margin: 30px 30px 20px 40px;">To-do List</h3>
                <div class="todo-container">
                    <div v-for="todo in storeGoal.tododata" :key="todo.todoId" class="todo-item">
                        <div class="todo-state" style="margin-right: 30px;">
                            <button v-if="todo.todoState" class="bi bi-check-circle-fill todo-button-plus" @click="changeState(todo.todoId)"></button>
                            <button v-else class="empty-circle todo-button-fail" @click="changeState(todo.todoId)"></button>
                        </div>
                        <div class="todo-content" @click="toggleEdit(todo)">
                            <div v-if="editingTodoId !== todo.todoId">
                                <button class="todo-button">{{ todo.todoContent }}</button>
                            </div>
                            <div v-else>
                                <input v-model="editedTodoContent" type="text" @click.stop class="todo-input"/>
                                <button @click="editTodo(todo)" class="edit-button">저장</button>
                                <button @click="deleteTodo(todo)" class="delete-button">삭제</button>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="add-todo-container">
                    <input v-if="!checkchage" type="text" v-model="newtodo.todoContent" class="new-todo-input"/>
                    <button v-if="checkchage" class="bi bi-plus-lg add-button" @click="plusclick"></button>
                    <div v-else>
                        <button class="cancel-button" @click="plusclick">Cancel</button>
                        <button class="add-button" @click="addTodo(newtodo)" :disabled="!newtodo.todoContent.trim()">Add todo</button>
                    </div>
                </div>
                
                <div v-if="dateString === data.date">
                    <div v-if="!isPosted">
                        <button @click="$router.push({ name: 'Post' })" class="post-button">Update Todos</button>
                    </div>
                </div>
            </div>
            <div class="goalwrite-container" v-else>               
                <div class="Addgoal-container">
                    <p  class="controller actions task_actions full_width_actions">
                        <button v-if="!showTaskInput" type="button" data-add-task-navigation-element="true" aria-hidden="false" class="plus_add_button" @click="toggleTaskInput">
                            <div class="icon_add" aria-hidden="true">
                                <svg width="20" height="20">
                                    <path fill="currentColor" fill-rule="evenodd" d="M6 6V.5a.5.5 0 0 1 1 0V6h5.5a.5.5 0 1 1 0 1H7v5.5a.5.5 0 1 1-1 0V7H.5a.5.5 0 0 1 0-1H6z"></path>
                                </svg>
                            </div>
                            <div style="font-size: 24px">
                                Add Goal
                            </div>
                        </button>
                    </p>
                </div>
                <div v-if="showTaskInput" class="task-input-container">
                    <input type="text" v-model="newgoal.goalContent" class="task-input" placeholder="Enter a new Goal"/>
                    <span class="task-buttons">
                        <button @click="toggleTaskInput" class="cancel-task-button">Cancel</button>
                        <button @click="goalCreate(newgoal)" class="add-task-button" :disabled="!newgoal.goalContent.trim()">Add Goal</button>
                    </span>
                </div>
                <div class="notodo-container">
                    <img src="/src/resources/imgs/todopage2.jpg" alt="투두페이지이미지" class="notodo-img"><br>
                    <h2>What do you need to get done ?</h2>
                    <h3>Click + to add a goal.</h3>
                </div>
            </div>
        </div>
        <div v-else>
            <div v-if="store.goaldata && store.goaldata.length > 0" class="goal-container">
                <h2>Goal : <button class="goal-button">{{ store.goaldata[0].goalContent }}</button></h2>
                <div class="todo-container">
                    <h3>{{ data.date }} To-do List</h3>
                    <div v-for="todo in storeGoal.tododata" :key="todo.todoId" class="todo-item">
                        <button class="todo-button">{{ todo.todoContent }}</button>
                        <div class="todo-state">
                            <button v-if="todo.todoState" class="bi bi-check-circle-fill todo-button-plus"></button>
                            <button v-else class="bi bi-x-circle-fill todo-button-fail"></button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="notodo-container" v-else>
                <img src="/src/resources/imgs/todopage3.jpg" alt="투두페이지이미지" class="notodo-img">
                <h2>No goals for this day...</h2>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useGoalStore } from '@/stores/goal';
import { useTodoStore } from '@/stores/todo';
import { usePostStore } from '@/stores/post';

const store = useGoalStore();
const storeGoal = useTodoStore();
const postStore = usePostStore();
const id = sessionStorage.getItem('loginUserId');
const today = new Date();
const year = today.getFullYear();
const month = ('0' + (today.getMonth() + 1)).slice(-2);
const day = ('0' + today.getDate()).slice(-2);
const dateString = year + '-' + month + '-' + day;

const data = ref({
    date: dateString
});

onMounted(() => {
    check();
});

const editingTodoId = ref(null);
const editedTodoContent = ref('');
const editingGoal = ref(false);
const editedGoalContent = ref('');

const getPostsListByUserId = async () => {
    try {
        await postStore.getPostsListByUserId(id);
    } catch (error) {
        console.error(error);
    }
};

const check = async () => {
    try {
        await store.getGoalData(id, data.value.date);
        await recheck(store.goaldata[0].goalId);
        await postStore.getPostsListByUserId(id);
    } catch (error) {
        console.error(error);
    }
};

const recheck = async (goalid) => {
    try {
        await storeGoal.getTodoData(goalid);
    } catch (error) {
        console.error(error);
    }
};

const editTodo = async (todo) => {
    try {
        const todoes = {
            "todoId": "",
            "todoGoalId": 0,
            "todoContent": editedTodoContent.value,
            "todoState": ""
        };
        await storeGoal.UpdateTodoData(todo.todoId, todoes);
        editingTodoId.value = null;
        await storeGoal.getTodoData(store.goaldata[0].goalId);
    } catch (error) {
        console.error(error);
    }
};

const newtodo = ref({
    "todoId": "",
    "todoGoalId": "",
    "todoContent": "",
    "todoState": ""
});

const checkchage = ref(true);
const plusclick = function () {
    checkchage.value = !checkchage.value;
    newtodo.value.todoGoalId = store.goaldata[0].goalId;
};

const addTodo = async (newtodo) => {
    try {
        checkchage.value = !checkchage.value;
        await storeGoal.addTodoData(newtodo);
        await storeGoal.getTodoData(store.goaldata[0].goalId);
        newtodo.todoContent = "";
    } catch (error) {
        console.error(error);
    }
};

const deleteTodo = async (todo) => {
    try {
        await storeGoal.DeleteTodoData(todo.todoId);
        await storeGoal.getTodoData(store.goaldata[0].goalId);
    } catch (error) {
        console.error(error);
    }
};

const toggleEdit = async (todo) => {
    if (editingTodoId.value === todo.todoId) {
        editingTodoId.value = null;
    } else {
        editingTodoId.value = todo.todoId;
        editedTodoContent.value = todo.todoContent;
    }
};

const newgoal = ref({
    "goalId": "",
    "goalUserId": id,
    "goalRegDate": "",
    "goalContent": ""
});

const goalCreate = async (newgoal) => {
    try {
        newgoal.goalRegDate = data.value.date;
        await store.createGoal(newgoal);
        await check();
    } catch (error) {
        console.error(error);
    }
};

const changeState = async (todoid) => {
    try {
        await storeGoal.UpdateState(todoid);
        await check();
    } catch (error) {
        console.error(error);
    }
};

const toggleEditGoal = () => {
    editingGoal.value = !editingGoal.value;
    if (editingGoal.value) {
        editedGoalContent.value = store.goaldata[0].goalContent;
    }
};

const editGoal = async () => {
    try {
        store.goaldata[0].goalContent
        const updatedGoal = {
            goalId: store.goaldata[0].goalId,
            goalUserId: store.goaldata[0].goalUserId,
            regDate: store.goaldata[0].regDate,
            goalContent: editedGoalContent.value,
        };
        await store.updateGoalData(updatedGoal);
        editingGoal.value = false;
        await check();
    } catch (error) {
        console.error(error);
    }
};

const showTaskInput = ref(false);
const newTaskContent = ref('');

const toggleTaskInput = () => {
  showTaskInput.value = !showTaskInput.value;
};

const addTask = () => {
  if (newTaskContent.value.trim() !== '') {
    // Logic to add the new task
    console.log('New Task:', newTaskContent.value);
    newTaskContent.value = '';
    showTaskInput.value = false;
  }
};
</script>


<style scoped>
.app-container {
    display: flex;
    flex-direction: column;
    /* align-items: center; */
    width: 900px;
    margin-right: 180px;
    margin-top: 20px;
    padding: 20px;
    background-color: rgb(253, 254, 254);
    font-family: 'Arial', sans-serif;
    border: 0px solid lightgray;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
    width: 93%;
    margin-top: 10px;
    margin-left: 30px;
    margin-bottom: 10px;
    display: flex;
    justify-content: flex-start;
    align-items: center;
}

.form-group label {
    font-size: 1.2em;
    margin-right: 10px;
}

.form-group input[type="date"] {
    padding: 7px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 1.1em;
}


.goal-container {
    width: 100%;
    background-color: #ffffff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.goal-container h2 {
    font-size: 1.5em;
    margin-bottom: 10px;
}

.goal-button {
    background: none;
    border: none;
    cursor: pointer;
    font-size: 1.2em;
    color: #000; 
}

.goal-button:hover {
    text-decoration: none; /* 기존 밑줄 제거 */
    color: #4267b2; /* 호버 시 글자 색상 변경 */
    font-size: 1.2em; /* 호버 시 글자 크기 변경 */
}

.editing-goal-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.goal-input {
    font-size: 1.2em;
    width: 100%;
    padding: 5px;
    border: 1px solid #ddd;
    border-radius: 5px;
    margin-bottom: 10px;
}

.Addgoal-container {
    margin-left:0;
}

.todo-container {
    width: 100%;
    background-color: #ffffff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.todo-item {
    display: flex;
    /* justify-content: space-between; */
    align-items: center;
    padding: 10px 0;
    border-bottom: 1px solid #ddd;
}

.todo-item:last-child {
    border-bottom: none;
}

.todo-button {
    background: none;
    border: none;
    cursor: pointer;
    font-size: 1.1em;
    color: #333;
    transition: color 0.3s;
}

.todo-button:hover {
    color: #4267b2;
}

.todo-input {
    width: 100%;
    padding: 5px;
    font-size: 1.1em;
    border: 1px solid #ddd;
    border-radius: 5px;
}

/* .editGoalform{
    display: flex;
    justify-content: space-between;
    align-items: center;
} */

.edit-button {
    background-color: #ffffff;
    border: 1px solid #ddd;
    color: rgb(136, 136, 136);
    cursor: pointer;
    margin-bottom: 10px;
    margin-left: 10px;
    padding: 8px 10px;
    border-radius: 7px;
    font-size: 1em;
    transition: background-color 0.3s;
    flex-shrink: 0;
}

.edit-button:hover {
    /* background-color: #45a049; */
}

.delete-button {
    background-color: red;
    color: white;
    border: none;
    cursor: pointer;
    padding: 5px 10px;
    border-radius: 5px;
    font-size: 1em;
    transition: background-color 0.3s;
    margin-left: 10px;
}

.delete-button:hover {
    background-color: darkred;
}

.todo-state {
    display: flex;
    align-items: center;
}

.todo-state button {
    display: inline-flex; 
    justify-content: center;
    align-items: center;
    background: none;
    width: 1em; 
    height: 1em; 
    /* border: none; */
    cursor: pointer;
    font-size: 1.5em;
}

.todo-button-plus {
    border: none;
    color: #4CAF50;
}

.todo-button-fail {
    color: transparent;
    background-color: transparent;
    border: 2px solid grey;
    border-radius: 50%;
    width: 1em; 
    height: 1em; 
    display: inline-flex; 
    justify-content: center;
    align-items: center;
}
.todo-button-fail:hover {
    background-color: #f0f0f0; 
}

.add-todo-container {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: flex-end;
    margin: 20px 0;
}

.new-todo-input {
    width: 100%;
    padding: 10px;
    font-size: 1.1em;
    border: 1px solid #ddd;
    border-radius: 5px;
    margin-bottom: 10px;
}

.add-button {
    background-color: #4caf50;
    color: white;
    border: none;
    cursor: pointer;
    padding: 8px 13px;
    font-size: 1.1em;
    border-radius: 5px;
    margin-left: 10px;
    transition: background-color 0.3s;
}

.add-button:hover {
    background-color: #4caf50;
}

.add-button:hover[disabled] {
    cursor:not-allowed;
}

.add-button:hover[disabled]:hover {
    background-color: #eda592; 
}

.cancel-button {
    background-color: rgb(221, 224, 214);
    color: black;
    font-weight: bold;
    border: none;
    cursor: pointer;
    padding: 8px 13px;
    font-size: 1.1em;
    border-radius: 5px;
    transition: background-color 0.3s;
    margin-left: 10px;
    margin-top: 10px;
    cursor: pointer;
}

.cancel-button:hover {
    background-color: rgb(190,190,190);
}

.post-button, .repost-button {
    width: 100%;
    padding: 15px;
    font-size: 1.2em;
    background-color: #3e75dc;
    color: white;
    border: none;
    border-radius: 10px;
    cursor: pointer;
    text-align: center;
    transition: background-color 0.3s;
}

.post-button:hover, .repost-button:hover {
    background-color: #0b57d0;
}

@media (max-width: 600px) {
    .goal-container, .todo-container {
        width: 90%;
    }
}

.plus_add_button {
  color: #808080;
  font-size: 14px;
  padding: 0 0 8px;
  text-align: left;
  text-decoration: none;
  white-space: nowrap;
  width: 100%;
  display: flex;
  align-items: center;
  border: none;
  background: none;
  cursor: pointer;
}

.icon_add {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 21px;
    height: 21px;
    border-radius: 50%;
    transition: background-color 0.3s;
}
.icon_add svg {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 6px; /* + 옮기기*/
    margin-left: 4px;
    width: auto;
    transition: fill 0.3s;
}

.icon_add:hover,
.plus_add_button:hover .icon_add {
    background-color: #ff9100;
}

.icon_add:hover svg,
.plus_add_button:hover .icon_add svg {
    color : #ffffff;
}

.plus_add_button:hover {
    color: #ff9100;
}



.plus_add_button .icon_add {
  margin-left: 55px;
  margin-right: 8px;
}

.task-input-container {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-left: 20px;
  margin-right: 20px;
  margin-top: 10px;
  background-color: #f9f9f9;
}

.task-input {
  width: 100%;
  padding: 8px;
  font-size: 14px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.add-task-button {
  background-color: #81c584;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.add-task-button:hover {
  background-color: #53b458;
}

.add-task-button[disabled] {
  cursor: not-allowed;
}

.add-task-button[disabled]:hover {
    background-color: #81c584;
}

.cancel-task-button {
  background-color: rgb(221, 214, 214);
  color: black;
  font-weight: bold;
  border: none;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
}

.cancel-task-button:hover {
  background-color: rgb(190, 190, 190);
}

.goalwrite-box {
    display: flex;
    align-items: center;
    margin-top: 30px;
    margin-bottom: 20px;
}

.goalwrite-container {
    margin-left: 120px;
    margin-bottom: 20px;
}

.notodo-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center; 
    margin-top: 20px;
}

.notodo-img {
    width: 50%;
    margin-bottom: 20px;
}

.notodo-container h2,
.notodo-container h3 {
    margin: 10px 0;
}
</style>